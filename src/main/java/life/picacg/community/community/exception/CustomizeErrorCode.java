package life.picacg.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    CONTRIBUTE_NOT_FOUND(2001,"查找内容不在啦= =，换个看看吧！"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN( 2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务器繁忙，请稍后重试"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
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
