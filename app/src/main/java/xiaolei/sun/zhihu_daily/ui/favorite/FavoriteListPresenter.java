package xiaolei.sun.zhihu_daily.ui.favorite;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbFavoriteCategory;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteContract.Presenter;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoriteListPresenter extends RxPresenter<FavoriteListContract.View> implements FavoriteListContract.Presenter {

    @Override
    public void getStories() {
        List<DbStory> list = DataSupport.findAll(DbStory.class);
        mView.setStories(list);
    }

    @Override
    public void getFavorateCategory() {
        List<DbFavoriteCategory> list = DataSupport.findAll(DbFavoriteCategory.class);
        List<String> stringList = new ArrayList<>();
        for (DbFavoriteCategory item:list){
            stringList.add(item.getName());
        }
        mView.setFavorateCategory(stringList);
    }
}
