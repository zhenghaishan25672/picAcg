package life.picacg.community.community.controller;

import life.picacg.community.community.dto.CommentDTO;
import life.picacg.community.community.dto.ResultDTO;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.model.Comment;
import life.picacg.community.community.model.User;
import life.picacg.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    //将对象自动序列化成json
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    //通过RequestBody可以接收json格式的数据
    public ResultDTO post(@RequestBody CommentDTO commentDTO,
                          HttpServletRequest request){
        //将json数据转成对象
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment  = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}


