package life.picacg.community.community.controller;

import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.User;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PublishService publishService;

//    @GetMapping("/hello")
//    public String hello(@RequestParam(name="name") String name, Model life.picacg.community.community.model){
//        life.picacg.community.community.model.addAttribute("name",name);
//        return "index";
//    }

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        //通过cookie中的token验证对应用户信息
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    //如果能找到对应用户信息，则登录成功，更新登录状态
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        //分页处理
        PaginationDTO pagination = publishService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
