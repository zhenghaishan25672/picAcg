package life.picacg.community.community.controller;

import life.picacg.community.community.mapper.PublishMapper;
import life.picacg.community.community.mapper.UserMapper;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PublishMapper publishMapper;

    //普通跳转到publish页面
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    //按下按钮发起post请求后（post请求失败），回调到publish页面
    @PostMapping("/publish")
    public String doPublish(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("tag") String tag,
        HttpServletRequest request,
        Model model){

        //点击登录按钮前，验证用户登录状态
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length !=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        //将页面中数据暂存
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(user == null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        if(title ==null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description ==null || description == ""){
            model.addAttribute("error","标题说明不能为空");
            return "publish";
        }
        if(tag ==null || tag == ""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        //如果验证信息通过，将数据存入publish model
        Publish publish = new Publish();
        publish.setTag(tag);
        publish.setTitle(title);
        publish.setDescription(description);
        publish.setCreator(user.getId());
        publish.setGmtCreate(System.currentTimeMillis());
        publish.setGmtModified(publish.getGmtCreate());
        //将数据存入publish表
        publishMapper.create(publish);
        return "redirect:/";
    }
}
