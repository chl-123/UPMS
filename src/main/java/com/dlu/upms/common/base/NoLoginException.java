package com.dlu.upms.common.base;

/**
 * @ClassName: BusinessException
 * @Description: TODO
 */
public class NoLoginException extends RuntimeException {

    /**
     * Comment for &lt;code&gt;serialVersionUID&lt;/code&gt;
     */
    private static final long serialVersionUID = 3455708526465670030L;

    private int code;

    private String msg;

    public NoLoginException() {
        this.code = 10000011;
        this.msg = "请先登录";
    }

    public NoLoginException(String message) {
        this.code = 10000011;
        this.msg = message;
    }

    public NoLoginException(Throwable cause) {
        super(cause);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getExceptionCode() {
        return code;
    }

    public void setExceptionCode(int code) {
        this.code = code;
    }

    public String getExceptionMsg() {
        return msg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.msg = exceptionMsg;
    }
}