package life.picacg.community.community.service;

import life.picacg.community.community.enums.CommentTypeEnum;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import life.picacg.community.community.mapper.CommentMapper;
import life.picacg.community.community.mapper.PublishExtMapper;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.model.Comment;
import life.picacg.community.community.model.Publish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private PublishExtMapper publishExtMapper;

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
}
