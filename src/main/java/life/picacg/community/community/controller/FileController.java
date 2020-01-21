package life.picacg.community.community.controller;

import life.picacg.community.community.dto.FileDTO;
import life.picacg.community.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;
//    private AlicloudProvider alicloudProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletRequest request){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file =  multipartRequest.getFile("editormd-image-file");
        String filePath = fileService.editMovieInfo(file,file.getOriginalFilename());
//        String filePath = alicloudProvider.upload(file.getInputStream(),file.getOriginalFilename());
//        FileDTO fileDTO = new FileDTO();
//        fileDTO.setSuccess(1);
//        fileDTO.setUrl(filePath);
//        return fileDTO;

        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(filePath);
        return fileDTO;
    }
}
