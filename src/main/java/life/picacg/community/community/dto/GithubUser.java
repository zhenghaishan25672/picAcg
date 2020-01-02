package life.picacg.community.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    //fastjson可以自动将下划线转换成驼峰命名
    private String avatar_url;
}
