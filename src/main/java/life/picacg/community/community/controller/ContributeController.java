package life.picacg.community.community.controller;

import life.picacg.community.community.dto.CommentDTO;
import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.enums.CommentTypeEnum;
import life.picacg.community.community.service.CommentService;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ContributeController {

    @Autowired
    private PublishService publishService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/contribute/{id}")
    public String contribute(@PathVariable(name = "id") Long id, Model model) {
        PublishDTO publishDTO = publishService.getById(id);
        List<PublishDTO> relatePublishes = publishService.selectRelated(publishDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.PUBLISH);

        //累加阅读数
        publishService.incView(id);
        model.addAttribute("publish", publishDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatePublishes", relatePublishes);
        return "contribute";
    }
}
