package life.picacg.community.community.service;

import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    public String editMovieInfo(MultipartFile file,String objectName) {
            String generateFilePath;
            String[] filePath = objectName.split("\\.");

            //判断文件类型及存储路径
            if(filePath.length>1){
                switch(filePath[filePath.length-1]) {
                    case "jpg":
                    case "png":
                    case "bmp":
                    case "gif":
                    case "jpeg":
                        generateFilePath = "/images/user/" + UUID.randomUUID().toString() + "." + filePath[filePath.length - 1];
                        break;
                    default:
                        throw new CustomizeException(CustomizeErrorCode.FILE_STYLE_FAIL);
                }
            }else{
                throw new CustomizeException(CustomizeErrorCode.FILE_STYLE_FAIL);
            }

            String absoultPath = upload(file,generateFilePath);

            return generateFilePath;
    }

    public String upload(MultipartFile file, String path){
        String realpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static"+path;
        File dest = new File(realpath);
        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        // 保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return realpath;
    }
}
