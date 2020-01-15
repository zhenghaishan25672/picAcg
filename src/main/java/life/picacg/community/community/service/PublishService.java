package life.picacg.community.community.service;

import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import life.picacg.community.community.mapper.PublishExtMapper;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.PublishExample;
import life.picacg.community.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class PublishService {

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private PublishExtMapper publishExtMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = (int) publishMapper.countByExample(new PublishExample());
        Integer totalPage;

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        //将每一页的列表返回到数组
        PublishExample publishExample = new PublishExample();
        publishExample.setOrderByClause("gmt_create desc");
        List<Publish> publishes = publishMapper.selectByExampleWithBLOBsWithRowbounds(publishExample, new RowBounds(offset, size));
        List<PublishDTO> publishDTOList = new ArrayList<>();
        //

        for (Publish publish : publishes) {
            User user = userMapper.selectByPrimaryKey(publish.getCreator());
            PublishDTO publishDTO = new PublishDTO();
            //运用工具类将publish的值反射到publishDTO中
            BeanUtils.copyProperties(publish, publishDTO);
            //将user中的creator字段放入publishDTO
            publishDTO.setUser(user);
            //最后将publishDTO的内容添加到publishDTOList中
            publishDTOList.add(publishDTO);
        }
        paginationDTO.setData(publishDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        PublishExample publishExample = new PublishExample();
        publishExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) publishMapper.countByExample(publishExample);

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        //将每一页的列表返回到数组
        PublishExample example = new PublishExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Publish> publishes = publishMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<PublishDTO> publishDTOList = new ArrayList<>();
        //

        for (Publish publish : publishes) {
            User user = userMapper.selectByPrimaryKey(publish.getCreator());
            PublishDTO publishDTO = new PublishDTO();
            //运用工具类将publish的值反射到publishDTO中
            BeanUtils.copyProperties(publish, publishDTO);
            //将user中的creator字段放入publishDTO
            publishDTO.setUser(user);
            //最后将publishDTO的内容添加到publishDTOList中
            publishDTOList.add(publishDTO);
        }
        paginationDTO.setData(publishDTOList);

        return paginationDTO;
    }

    public PublishDTO getById(Long id) {
        Publish publish = publishMapper.selectByPrimaryKey(id);
        if(publish == null){
            throw new CustomizeException(CustomizeErrorCode.CONTRIBUTE_NOT_FOUND);
        }
        PublishDTO publishDTO = new PublishDTO();
        BeanUtils.copyProperties(publish, publishDTO);
        User user = userMapper.selectByPrimaryKey(publish.getCreator());
        publishDTO.setUser(user);
        return publishDTO;
    }

    public void createOrUpdate(Publish publish) {
        if(publish.getId() == null){
            //创建
            publish.setGmtCreate(System.currentTimeMillis());
            publish.setGmtModified(publish.getGmtCreate());
            publish.setViewCount(0);
            publish.setLikeCount(0);
            publish.setCommentCount(0);
            publishMapper.insert(publish);
        }else{
            //更新
            Publish dbQuestion = publishMapper.selectByPrimaryKey(publish.getId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.CONTRIBUTE_NOT_FOUND);
            }

//            if (dbQuestion.getCreator().longValue() != publish.getCreator().longValue()) {
//                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
//            }

            Publish updateQuestion = new Publish();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(publish.getTitle());
            updateQuestion.setDescription(publish.getDescription());
            updateQuestion.setTag(publish.getTag());
            PublishExample example = new PublishExample();
            example.createCriteria()
                    .andIdEqualTo(publish.getId());
            int updated = publishMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.CONTRIBUTE_NOT_FOUND);
            }

        }
    }

    public void incView(Long id) {
        Publish publish = new Publish();
        publish.setId(id);
        publish.setViewCount(1);
        publishExtMapper.incView(publish);
    }

    /*标签关联关系*/
    public List<PublishDTO> selectRelated(PublishDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining("|"));

        Publish publish = new Publish();
        publish.setId(queryDTO.getId());
        publish.setTag(regexpTag);

        List<Publish> publishes = publishExtMapper.selectRelated(publish);
        List<PublishDTO> publishDTOS = publishes.stream().map(q -> {
            PublishDTO publishDTO = new PublishDTO();
            BeanUtils.copyProperties(q,publishDTO);
            return publishDTO;
        }).collect(Collectors.toList());
        return publishDTOS;
    }
}
