package xiaolei.sun.zhihu_daily.network.entity;

import java.io.Serializable;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class TopStoriesBean implements Serializable{

    /**
     * image : http://pic2.zhimg.com/615c27fe9835358297bed972e52ed791.jpg
     * type : 0
     * id : 8815855
     * ga_prefix : 092118
     * title : 中国也有了米其林餐厅 ：这些我喜欢， 那些我可能不会去
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
}
