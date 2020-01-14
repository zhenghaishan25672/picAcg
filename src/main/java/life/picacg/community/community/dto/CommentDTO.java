package life.picacg.community.community.dto;

import life.picacg.community.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long LikeCount;
    private Integer commentCount;
    private User user;
}
