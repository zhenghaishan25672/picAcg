package life.picacg.community.community.service;

import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.PublishExample;
import life.picacg.community.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishService {

    @Autowired
    private PublishMapper publishMapper;

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
        List<Publish> publishes = publishMapper.selectByExampleWithBLOBsWithRowbounds(new PublishExample(), new RowBounds(offset, size));
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
        paginationDTO.setPublishes(publishDTOList);

        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PublishExample publishExample = new PublishExample();
        publishExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) publishMapper.countByExample(publishExample);
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
        paginationDTO.setPublishes(publishDTOList);

        return paginationDTO;
    }

    public PublishDTO getById(Integer id) {
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
            publishMapper.insert(publish);
        }else{
            //更新
            Publish updatePublish = new Publish();
            updatePublish.setGmtModified(System.currentTimeMillis());
            updatePublish.setTitle(publish.getTitle());
            updatePublish.setDescription(publish.getDescription());
            updatePublish.setTag(publish.getTag());
            PublishExample example = new PublishExample();
            example.createCriteria().andCreatorEqualTo(publish.getId());
            int updated = publishMapper.updateByExampleSelective(updatePublish, example);
            if(updated != 0){
                throw new CustomizeException(CustomizeErrorCode.CONTRIBUTE_NOT_FOUND);
            }
        }
    }
}
