package xiaolei.sun.zhihu_daily.network.entity.leancloud;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/11/2.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class FavoriteRequest {

    private String userId;
    private String storyId;
    private String category;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
