package life.picacg.community.community.dto;

import life.picacg.community.community.model.User;
import lombok.Data;

/*
类和类之间传输用dto，网络传输的对象
 */
@Data
public class PublishDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
