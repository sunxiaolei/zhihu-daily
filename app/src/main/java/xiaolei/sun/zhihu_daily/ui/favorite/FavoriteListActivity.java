package xiaolei.sun.zhihu_daily.ui.favorite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.expandable.view.ExpandableView;

import java.util.List;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.widget.quickadapter.BaseQuickAdapter;
import xiaolei.sun.zhihu_daily.widget.quickadapter.SpringView;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;

/**
 * Created by sunxl8 on 2016/9/30.
 */

public class FavoriteListActivity extends BaseSwipeBackActivity<FavoriteListContract.Presenter>
        implements BaseQuickAdapter.RequestLoadMoreListener, SpringView.OnFreshListener, FavoriteListContract.View {

    private ListView lvCategory;

    private List<String> listCategory;

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
    protected FavoriteListPresenter createPresenter() {
        return new FavoriteListPresenter();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_favorite_list;
    }

    @Override
    public void init() {
        lvCategory = (ListView) findViewById(R.id.lv_favorate_category);

        v = LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_recyclerview, null, false);
        rv = (RecyclerView) v.findViewById(R.id.rv_normal);
        rv.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
        mPresenter.getFavorateCategory();



    }

    @Override
    public void setFavorateCategory(List<String> list) {
        listCategory = list;
        lvCategory.setAdapter(new CategoryAdapter());
    }

    @Override
    public void setStories(List<DbStory> listStories) {

    }

    View v;
    RecyclerView rv;
    class CategoryAdapter extends BaseAdapter {

        public CategoryAdapter() {

        }

        @Override
        public int getCount() {
            return listCategory.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_expandableview, null, false);
            ExpandableView ev = (ExpandableView) view.findViewById(R.id.ev_item);
            ev.fillData(0, listCategory.get(i));
            rv.setAdapter(new Madapter());
            ev.addContentView(view);

            return view;
        }
    }

    class Madapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        String[] strs = {"001", "002", "003", "004"};

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Mholder(LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_textview, null, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Mholder) {
                ((Mholder) holder).tv.setText(strs[position]);
            }
        }

        @Override
        public int getItemCount() {
            return strs.length;
        }
    }

    class Mholder extends RecyclerView.ViewHolder {

        TextView tv;

        public Mholder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_normal);
        }
    }
}
