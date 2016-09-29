package xiaolei.sun.zhihu_daily.ui.favorite;

import org.litepal.crud.DataSupport;

import java.util.List;

import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteContract.Presenter;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoritPresenter implements Presenter {

    private FavoriteContract.View mView;

    public FavoritPresenter(FavoriteContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getStories() {
        List<DbStory> list = DataSupport.findAll(DbStory.class);
        mView.setStories(list);
    }
}
