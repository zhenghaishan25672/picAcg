create table PUBLISH
(
	ID BIGINT auto_increment,
	TITLE VARCHAR2(50),
	DESCRIPTION TEXT,
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	CREATOR BIGINT not null,
	COMMENT_COUNT INT default 0,
	VIEW_COUNT INT default 0,
	LIKE_COUNT INT default 0,
	TAG VARCHAR2(256),
	constraint PUBLISH_PK
		primary key (ID)
);



