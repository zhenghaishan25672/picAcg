package life.picacg.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{

    CONTRIBUTE_NOT_FOUND("查找内容不在啦= =，换个看看吧！");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
