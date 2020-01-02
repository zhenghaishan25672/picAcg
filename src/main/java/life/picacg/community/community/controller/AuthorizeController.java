package life.picacg.community.community.controller;

import life.picacg.community.community.dto.AccessTokenDTO;
import life.picacg.community.community.dto.GithubUser;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.User;
import life.picacg.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    //依赖注入
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

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
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);

        //将登录授权返回的code，再次发往服务器请求验证
        String accessToken = githubProvider.getAccessTokenDTO(accessTokenDTO);

        //根据服务器传回的access_token获取用户数据信息
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        System.out.println(githubUser.getName());

        //收到数据后往model中填值，再插入数据库
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            String token = UUID.randomUUID().toString();//这里的token为之后该网站验证用的token，而非github的
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            //登录成功，写cookie和session
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败，重新登陆
            return "redirect:/";
        }
    }

}
