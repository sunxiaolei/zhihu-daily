package xiaolei.sun.zhihu_daily.ui.favorite;


import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteContract.Presenter;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoritPresenter extends RxPresenter<FavoriteContract.View> implements Presenter {

    @Override
    public void getStory(String title) {


        //数据库获取收藏列表
//        List<DbStory> list = DbManager.getStoriesByTitle(title);
//        mView.setStory(list.get(0));
    }
}
