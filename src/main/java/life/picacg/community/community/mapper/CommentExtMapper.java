package life.picacg.community.community.mapper;

import life.picacg.community.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}