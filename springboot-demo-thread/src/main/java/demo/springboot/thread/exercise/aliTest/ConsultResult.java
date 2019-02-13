package demo.springboot.thread.exercise.aliTest;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 下午7:47
 */
public class ConsultResult {

    public ConsultResult(boolean isEnable, String errorCode) {
        this.isEnable = isEnable;
        this.errorCode = errorCode;
    }

    /**
     * 咨询结果是否可用
     */
    private boolean isEnable;

    /**
     * 错误码
     */
    private String errorCode;

    public boolean getIsEnable() {
        return isEnable;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ConsultResult{" +
                "isEnable=" + isEnable +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}