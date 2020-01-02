package life.picacg.community.community.dto;

import lombok.Data;

/*
类和类之间传输用dto，网络传输的对象
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
