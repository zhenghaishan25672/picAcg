package life.picacg.community.community.controller;

import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 */
@Controller
public class IndexController {
    @Autowired
    private PublishService publishService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size
    ){
        //分页处理
        PaginationDTO pagination = publishService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
