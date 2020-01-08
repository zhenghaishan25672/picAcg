package life.picacg.community.community.dto;

import lombok.Data;

/*数据传输对象(Data Transfer Object)*/
/*
  1.依据现有的类代码，即可方便的构造出DTO对象，而无需重新进行分析。
  2.减少请求次数，大大提高效率。
  3.按需组织DTO对象，页面需要的字段我才组织，不需要的我不组织，可以避免传输整个表的字段，一定程度上提高了安全性。
 */
@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
