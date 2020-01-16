## README
```
git commit --amend --no-edit
```
取消上一步commit操作

## 资料
[bootcss](https://v3.bootcss.com)

[bilibili教学视频](https://www.bilibili.com/video/av50200264)

[springboot 文档](https://spring.io/guides)

[springboot web](https://spring.io/guides/gs/serving-web-content)

[github app](https://developer.github.com/apps/building-github-apps/creating-a-github-app)

[flyway](https://flywaydb.org/getstarted/)

[lombok](https://projectlombok.org/setup/maven)

![时序图](https://raw.githubusercontent.com/zhenghaishan25672/notes/master/img/server/githubApp_token.jpg)

## 脚本
```bash
mvn flyway:migrate
```
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

## 部署
yum update
yum install git 
git clone https://github.com/zhenghaishan25672/picAcg.git
yum install maven (java -version mvn -v)
mvn clean compile package
cp src/main/resources/application.properties src/main/resources/application-production.properties
vim src/main/resources/application-production.properties
mvn package
java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar
