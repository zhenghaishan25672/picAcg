create table COMMENT
(
	ID BIGINT auto_increment
		primary key,
	PARENT_ID BIGINT not null,
	TYPE INT not null,
	COMMENTATOR BIGINT not null,
	GMT_CREATE BIGINT not null,
	GMT_MODIFIED BIGINT not null,
	LIKE_COUNT BIGINT default 0,
	CONTENT VARCHAR(1024),
	COMMENT_COUNT INT default 0
);

