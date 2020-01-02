package life.picacg.community.community.provider;

import com.alibaba.fastjson.JSON;
import life.picacg.community.community.dto.AccessTokenDTO;
import life.picacg.community.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessTokenDTO(AccessTokenDTO accessTokenDTO){
        //用户发起post请求用access_token与code传给服务器，服务器将access_token返回，此后只要携带token就可以直接访问用户数据

        //设置请求格式
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        //accessTokenDTO中含有callback服务器传回来的code
        //请求成功后服务器将用户申请的token返回到body
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

        //body返回值为 access_token=a56ba80154c8525c8ffe7fbe328c8428fcbb4423&scope=user&token_type=bearer
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        //服务器根据返回的code确认验证，传回一个access_token
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
//            System.out.println(string);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //根据服务器传回的access_token获取用户数据信息
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //将获取到的json对象直接转化成类，就不需要一条条去解析了
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
