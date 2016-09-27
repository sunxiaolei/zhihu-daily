package xiaolei.sun.zhihu_daily.ui.story;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryContract {

    interface View{

        void setImageUrl(String url);

        void setTitle(String string);

        void loadData(String string);

    }

    interface Presenter {

        void getNews(int storyId);

        void getCss();
    }
}
