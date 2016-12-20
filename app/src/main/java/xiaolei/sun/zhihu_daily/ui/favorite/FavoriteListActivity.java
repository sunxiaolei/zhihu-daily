package xiaolei.sun.zhihu_daily.ui.favorite;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRelationResponse;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.ui.story.StoryActivity;
import xiaolei.sun.zhihu_daily.widget.ExpandableView;
import xiaolei.sun.zhihu_daily.widget.colorful.Colorful;

/**
 * Created by sunxl8 on 2016/9/30.
 */

public class FavoriteListActivity extends BaseSwipeBackActivity {

    private RecyclerView rvCategory;

    private List<String> listCategory;

    private List<FavoriteRelationResponse.ResultsBean> listResult;

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

        listCategory = new ArrayList<>();
        rvCategory = (RecyclerView) findViewById(R.id.rv_favorate_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(FavoriteListActivity.this));
        getCategory();
    }

    private void getCategory() {
        showLoading();
        Map<String, String> map = new HashMap<>();
        JsonObject obj = new JsonObject();
        obj.addProperty("userId", ZhihuDailyApplication.user.getObjectId());
        map.put("where", obj.toString());
        LeanCloudRequest.getFavoriteRelation(map)
                .subscribe(response -> {
                    dismissLoading();
                    listResult = response.getResults();
                    if (listResult != null && listResult.size() > 0) {
                        for (FavoriteRelationResponse.ResultsBean bean : listResult) {
                            if (!listCategory.contains(bean.getCategory())) {
                                listCategory.add(bean.getCategory());
                            }
                        }
                        rvCategory.setAdapter(new Eadapter());
                    }
                });

//        listCategory = DbManager.getFavorateCategory();
//        rvCategory.setAdapter(new Eadapter());
    }

    /**
     * 条目
     */
    class Madapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<FavoriteRelationResponse.ResultsBean> listStories;

        public Madapter(List<FavoriteRelationResponse.ResultsBean> listStories) {
            this.listStories = listStories;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Mholder(LayoutInflater.from(FavoriteListActivity.this).inflate(R.layout.item_textview, null, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof Mholder) {
                ((Mholder) holder).tv.setText(listStories.get(position).getStoryTitle());
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
                tv.setOnClickListener(view -> {
                    Intent intent = new Intent(FavoriteListActivity.this, StoryActivity.class);
                    intent.putExtra("STORY_ID", Integer.parseInt(listStories.get(getAdapterPosition()).getStoryId()));
                    FavoriteListActivity.this.startActivity(intent);
                });
            }
        }
    }


    /**
     * 类别
     */
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
//                List<DbStory> list = DbManager.getStoriesByFavorateCategory(listCategory.get(position));
//                rv.setAdapter(new Madapter(list));
                List<FavoriteRelationResponse.ResultsBean> list = new ArrayList<>();
                for (FavoriteRelationResponse.ResultsBean bean : listResult) {
                    if (listCategory.get(position).equals(bean.getCategory())) {
                        list.add(bean);
                    }
                }
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
