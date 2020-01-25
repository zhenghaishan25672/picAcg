package life.picacg.community.community.controller;

import life.picacg.community.community.cache.HotTagCache;
import life.picacg.community.community.dto.HotTagDTO;
import life.picacg.community.community.dto.PaginationDTO;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *
 */
@Controller
public class IndexController {
    @Autowired
    private PublishService publishService;

    @Autowired
    private HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search,
                        @RequestParam(name = "tag",required = false) String tag
    ){
        //分页处理
        PaginationDTO pagination = publishService.list(search,tag,page,size);
        //热门标签
        Map<String, HotTagDTO> tagsMap = hotTagCache.getHots();

        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("tagsMap",tagsMap);
        model.addAttribute("tag",tag);
        return "index";
    }
}
