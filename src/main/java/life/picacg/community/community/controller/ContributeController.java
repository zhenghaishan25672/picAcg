package life.picacg.community.community.controller;

import life.picacg.community.community.dto.PublishDTO;
import life.picacg.community.community.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ContributeController {

    @Autowired
    public PublishService publishService;

    @GetMapping("/contribute/{id}")
    public String contribute(@PathVariable(name = "id") Integer id,
                             Model model){

        PublishDTO publishDTO = publishService.getById(id);
        model.addAttribute("publish",publishDTO);
        return "contribute";
    }
}
