package xiaolei.sun.zhihu_daily.ui.story;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.ui.base.IView;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryContract {

    interface View extends IView {

        void setImageUrl(String url);

        void setTitle(String string);

        void loadData(String string);

        void favorite(String message);

        void setFavorateCategory(List<String> list);

    }

    interface Presenter extends IPresenter<View> {

        void getNews(int storyId);

        void getCss();

        void favorite(String favoriteName);

        void getFavorateCategory();
    }
}
