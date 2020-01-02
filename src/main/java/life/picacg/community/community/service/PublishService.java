package life.picacg.community.community.service;

import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.User;
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
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = publishMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        Integer offset = size * (page - 1);
        //将每一页的列表返回到数组
        List<Publish> publishes = publishMapper.list(offset, size);
        List<PublishDTO> publishDTOList = new ArrayList<>();
        //

        for (Publish publish : publishes) {
            User user = userMapper.findById(publish.getCreator());
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

}
