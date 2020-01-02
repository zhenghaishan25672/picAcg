create table publish
(
	id int auto_increment,
	title VARCHAR2(50),
	description TEXT,
	gmt_create BIGINT,
	gmt_modified BIGINT,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag VARCHAR2(256),
	constraint publish_pk
		primary key (id)
);

