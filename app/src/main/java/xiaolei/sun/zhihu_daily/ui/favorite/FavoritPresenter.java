package xiaolei.sun.zhihu_daily.ui.favorite;


import java.util.List;

import xiaolei.sun.zhihu_daily.db.DbManager;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteContract.Presenter;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoritPresenter extends RxPresenter<FavoriteContract.View> implements Presenter {

    @Override
    public void getStory(String title) {
        List<DbStory> list = DbManager.getStoriesByTitle(title);
        mView.setStory(list.get(0));
    }
}
