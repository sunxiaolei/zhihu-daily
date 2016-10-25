package xiaolei.sun.zhihu_daily.ui.favorite;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expandable.view.ExpandableView;

import java.util.List;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.db.DbManager;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;

/**
 * Created by sunxl8 on 2016/9/30.
 */

public class FavoriteListActivity extends BaseSwipeBackActivity {

    private RecyclerView rvCategory;

    private List<String> listCategory;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.favorite_list));
        rvCategory = (RecyclerView) findViewById(R.id.rv_favorate_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
        listCategory = DbManager.getFavorateCategory();
        rvCategory.setAdapter(new Eadapter());
    }

    class Madapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<DbStory> listStories;

        public Madapter(List<DbStory> listStories) {
            this.listStories = listStories;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Mholder(LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_textview, null, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Mholder) {
                ((Mholder) holder).tv.setText(listStories.get(position).getTitle());
            }
        }

        @Override
        public int getItemCount() {
            return listStories.size();
        }

        class Mholder extends RecyclerView.ViewHolder {

            TextView tv;

            public Mholder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_normal);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FavoriteListActivity.this, FavoriteActivity.class);
                        intent.putExtra("STORY_TITLE", listStories.get(getAdapterPosition()).getTitle());
                        FavoriteListActivity.this.startActivity(intent);
                    }
                });
            }
        }
    }


    class Eadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Eholder(LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_expandableview, null, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Eholder) {
                ((Eholder) holder).ev.fillData(0, listCategory.get(position));
                View v = LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_recyclerview, null, false);
                RecyclerView rv = (RecyclerView) v.findViewById(R.id.rv_normal);
                rv.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
                List<DbStory> list = DbManager.getStoriesByFavorateCategory(listCategory.get(position));
                rv.setAdapter(new Madapter(list));
                ((Eholder) holder).ev.addContentView(v);
            }
        }

        @Override
        public int getItemCount() {
            return listCategory.size();
        }

        class Eholder extends RecyclerView.ViewHolder {

            ExpandableView ev;

            public Eholder(View itemView) {
                super(itemView);
                ev = (ExpandableView) itemView.findViewById(R.id.ev_item);
            }
        }
    }

}
