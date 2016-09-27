package xiaolei.sun.zhihu_daily.network.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/27.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class BmobStory extends BmobObject{

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private SectionBean section;
    private int type;
    private int id;
    private String css;
}
