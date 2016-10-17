package xiaolei.sun.zhihu_daily.ui.favorite;

import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteFolderName;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.ui.base.IView;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoriteContract {

    interface View extends IView{

        void setStories(List<DbStory> list);
    }

    interface Presenter extends IPresenter<View>{

        void getStories();

    }
}
