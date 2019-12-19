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

![时序图](https://raw.githubusercontent.com/zhenghaishan25672/notes/master/img/server/githubApp_token.jpg)

## 脚本
```sql
create table USER
(
	ID INT auto_increment,
	ACCOUNT_ID VARCHAR(100),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);
```
