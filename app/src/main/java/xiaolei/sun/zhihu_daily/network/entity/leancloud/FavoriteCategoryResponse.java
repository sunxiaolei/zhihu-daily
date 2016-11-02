package xiaolei.sun.zhihu_daily.network.entity.leancloud;

import java.util.List;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/11/2.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class FavoriteCategoryResponse {


    /**
     * category : 社科
     * storyId : 8940934
     * userId : 58119bf4128fe1005ff3d885
     * createdAt : 2016-11-02T13:41:20.167Z
     * updatedAt : 2016-11-02T13:41:20.167Z
     * objectId : 5819ed00a22b9d0067a6bfb2
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String category;
        private String storyId;
        private String userId;
        private String createdAt;
        private String updatedAt;
        private String objectId;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStoryId() {
            return storyId;
        }

        public void setStoryId(String storyId) {
            this.storyId = storyId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }
    }
}
