package xiaolei.sun.zhihu_daily.ui.favorite;

import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteFolderName;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoriteContract {

    interface View {

        void setStories(List<DbStory> list);
    }

    interface Presenter {

        void getStories();

    }
}
