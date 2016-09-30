package xiaolei.sun.zhihu_daily.ui.favorite;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.customerview.quickadapter.BaseQuickAdapter;
import xiaolei.sun.zhihu_daily.customerview.quickadapter.SpringView;
import xiaolei.sun.zhihu_daily.ui.base.BaseOtherActivity;

/**
 * Created by sunxl8 on 2016/9/30.
 */

public class FavoriteListActivity extends BaseOtherActivity implements BaseQuickAdapter.RequestLoadMoreListener, SpringView.OnFreshListener{

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
    public int setContentViewId() {
        return R.layout.activity_favorite_list;
    }

    @Override
    public void init() {

    }
}
