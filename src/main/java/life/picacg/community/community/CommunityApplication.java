package life.picacg.community.community;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("life.picacg.community.community.mapper")
@EnableScheduling
public class CommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);

    }

}
