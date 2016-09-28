package xiaolei.sun.zhihu_daily.ui.story;

import cn.bmob.v3.exception.BmobException;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryContract {

    interface View{

        void setImageUrl(String url);

        void setTitle(String string);

        void loadData(String string);

        void favorite(String message);

    }

    interface Presenter {

        void getNews(int storyId);

        void getCss();

        void favorite();
    }
}
