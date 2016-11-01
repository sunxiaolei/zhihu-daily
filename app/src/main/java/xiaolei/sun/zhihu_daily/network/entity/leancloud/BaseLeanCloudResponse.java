package xiaolei.sun.zhihu_daily.network.entity.leancloud;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public class BaseLeanCloudResponse {


    /**
     * code : 217
     * error : Invalid username, it must be a non-blank string.
     */

    private int code;
    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
