package life.picacg.community.community.controller;

import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.model.Publish;
import life.picacg.community.community.model.User;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private PublishService publishService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        PublishDTO publish = publishService.getById(id);
        //将页面中数据暂存
        model.addAttribute("title", publish.getTitle());
        model.addAttribute("description", publish.getDescription());
        model.addAttribute("tag", publish.getTag());
        model.addAttribute("id", publish.getId());
        return "publish";
    }

    //普通跳转到publish页面
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    //按下按钮发起post请求后（post请求失败），回调到publish页面
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            HttpServletRequest request,
            Model model) {

        //将页面中数据暂存
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "标题说明不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        //如果验证信息通过，将数据存入publish model
        Publish publish = new Publish();
        publish.setId(id);
        publish.setTag(tag);
        publish.setTitle(title);
        publish.setDescription(description);
        publish.setCreator(user.getId());
        //将数据存入publish表
        publishService.createOrUpdate(publish);
        return "redirect:/";
    }
}
