package xiaolei.sun.zhihu_daily.network.entity.zhihu;

import java.io.Serializable;
import java.util.List;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoriesBean implements Serializable{

    /**
     * images : ["http://pic3.zhimg.com/800792d19b709bdea4ce5436bc33e19e.jpg"]
     * type : 0
     * id : 8816472
     * ga_prefix : 092122
     * title : 小事 · 被祸害的童年
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
