package xiaolei.sun.zhihu_daily.ui.favorite;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.widget.quickadapter.BaseQuickAdapter;
import xiaolei.sun.zhihu_daily.widget.quickadapter.SpringView;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;

/**
 * Created by sunxl8 on 2016/9/30.
 */

public class FavoriteListActivity extends BaseSwipeBackActivity implements BaseQuickAdapter.RequestLoadMoreListener, SpringView.OnFreshListener{

    @Override
    public void onLoadMoreRequested() {
        showToast("onLoadMoreRequested");
    }

    @Override
    public void onRefresh() {
        showToast("onRefresh");
    }

    @Override
    public void onLoadmore() {
        showToast("onLoadmore");
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_favorite_list;
    }

    @Override
    public void init() {

    }
}
