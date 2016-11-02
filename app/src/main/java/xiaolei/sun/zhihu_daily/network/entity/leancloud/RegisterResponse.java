package xiaolei.sun.zhihu_daily.network.entity.leancloud;

/**
 * Created by sunxl8 on 2016/11/2.
 */

public class RegisterResponse {

    /**
     * sessionToken : qmdj8pdidnmyzp0c7yqil91oc
     * createdAt : 2015-07-14T02:31:50.100Z
     * objectId : 55a47496e4b05001a7732c5f
     */

    private String sessionToken;
    private String createdAt;
    private String objectId;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
