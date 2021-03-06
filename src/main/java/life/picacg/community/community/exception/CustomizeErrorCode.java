package life.picacg.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    CONTRIBUTE_NOT_FOUND(2001,"查找内容不在啦= =，换个看看吧！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN( 2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器繁忙，请稍后重试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "读取信息失败"),
    NOTIFICATION_NOT_FOUND(2009, "信息查找失败"),
    FILE_STYLE_FAIL(2010, "文件类型错误"),
    FILE_SAVE_FAIL(2010, "文件保存失败"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
