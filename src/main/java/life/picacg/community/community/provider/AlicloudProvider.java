package life.picacg.community.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import life.picacg.community.community.exception.CustomizeErrorCode;
import life.picacg.community.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class AlicloudProvider {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    @Value("${alibaba.client.endpoint}")
    String endpoint;
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    @Value("${alibaba.client.id}")
    String accessKeyId;
    @Value("${alibaba.client.secret}")
    String accessKeySecret;
    @Value("${alibaba.client.bucketName}")
    String bucketName;

    public String upload(InputStream file, String objectName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String generateFilePath;
        String[] filePath = objectName.split("\\.");

        //判断文件类型及存储路径
        if(filePath.length>1){
            if(filePath[filePath.length-1].equals("jpg")) {
                generateFilePath = "images/" + UUID.randomUUID().toString() + "." + filePath[filePath.length - 1];
            }else{
                return null;
            }
        }else{
           throw new CustomizeException(CustomizeErrorCode.FILE_STYLE_FAIL);
        }

        try {
            ossClient.putObject(bucketName, generateFilePath, file);
            //设置过期时间
            Date expiration = new Date(new Date().getTime() + 3600 * 10000);
            URL url = ossClient.generatePresignedUrl(bucketName, generateFilePath, expiration);
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return generateFilePath;
    }
}
