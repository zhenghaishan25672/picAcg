package life.picacg.community.community.controller;

import life.picacg.community.community.dto.AccessTokenDTO;
import life.picacg.community.community.dto.GithubUser;
import life.picacg.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    //依赖注入
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")//将application.properties下的声明注入到这里
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirect.uri}")
    private String redirectUri;

    //登录获取授权后，github回调Redirect_uri携带code
    //spring会自动把上下文中的request放进来
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        String accessToken = githubProvider.getAccessTokenDTO(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
//        System.out.println(user.getName());
        if(user != null){
            //登录成功，写cookie和session
            request.getSession().setAttribute("user",user);
        }else{
            //登录失败，重新登陆
        }
        return "index";
    }

}
