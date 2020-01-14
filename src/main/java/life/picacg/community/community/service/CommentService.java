package life.picacg.community.community.service;

import life.picacg.community.community.dto.CommentDTO;
import life.picacg.community.community.enums.CommentTypeEnum;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import life.picacg.community.community.mapper.*;
import life.picacg.community.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private PublishExtMapper publishExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    //将下列所有的代码添加到一个事物中，当请求失败时，全部回滚
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

            //增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
        }else{
            //回复问题
            Publish publish = publishMapper.selectByPrimaryKey(comment.getParentId());
            if(publish == null){
                throw new CustomizeException(CustomizeErrorCode.CONTRIBUTE_NOT_FOUND);
            }
            /*
            * 在网络延迟和数据库抖动的情况下，可能存在评论插入成功，但是回复数未增加的情况
            * 这就需要引入事物的概念来处理
            * */
            commentMapper.insert(comment);
            publish.setCommentCount(1);
            publishExtMapper.incCommentCount(publish);
        }

    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人并转化为 Map
        UserExample userexample = new UserExample();
        userexample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userexample);
        Map<Long ,User> userMap = users.stream().collect(Collectors.toMap(user->user.getId(),user->user));

        //转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

}
