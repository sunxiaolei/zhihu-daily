package xiaolei.sun.zhihu_daily.ui.story;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.db.DbManager;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.widget.dialog.BottomSheetDialog;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/22.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoryActivity extends BaseSwipeBackActivity<StoryPresenter> implements StoryContract.View {

    private int storyId;
    private String storyTitle;

    /**
     * 收藏按钮
     */
    private FloatingActionButton btnFavorite;
    private CollapsingToolbarLayout collapsingToolbar;
    private SimpleDraweeView image;
    private AppBarLayout layoutTop;

    private WebView web;

    private BottomSheetDialog mBottomSheetDialog;

    private boolean isFavorite = false;

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_share:
                        break;
                    case R.id.action_settings:
                        break;
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutTop = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        layoutTop.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                }
            }
        });

        storyId = getIntent().getIntExtra("STORY_ID", 0);

        btnFavorite = (FloatingActionButton) findViewById(R.id.btn_story_favorite);

//        checkIsFavorite();


        web = (WebView) findViewById(R.id.web_activity_story);
        image = (SimpleDraweeView) findViewById(R.id.img_activity_story);
//        web.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return false;
//            }
//        });
        web.getSettings().setJavaScriptEnabled(true);

        showLoading();
        mPresenter.getNews(storyId);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (isFavorite) {
//                    showToast("已收藏");
//                } else {
//                    showBottomSheet();
//                }
                if (ZhihuDailyApplication.isLogin) {
                    showBottomSheet();
                } else {
                    showToast("请先登录");
                }
            }
        });
    }

    /**
     * 判断是否已收藏
     */
//    private void checkIsFavorite() {
//        if (DbManager.isFavorite(storyId)) {
//            isFavorite = true;
//            btnFavorite.setImageResource(R.drawable.ic_action_favorite_yet);
//        } else {
//            isFavorite = false;
//            btnFavorite.setImageResource(R.drawable.ic_action_favorite);
//        }
//    }
    @Override
    protected StoryPresenter createPresenter() {
        return new StoryPresenter(this);
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_story;
    }

    @Override
    public void setImageUrl(String url) {
        image.setImageURI(url);
    }

    @Override
    public void setTitle(String string) {
        storyTitle = string;
        collapsingToolbar.setTitle(string);
    }

    @Override
    public void loadData(String string) {
        dismissLoading();
        web.loadData(string, "text/html; charset=UTF-8", null);
    }

    @Override
    public void favoriteResult(int result) {
        mBottomSheetDialog.dismiss();
        switch (result) {
            case StoryPresenter.FAVORITE_YET:
                showToast("已收藏过");
                break;
            case StoryPresenter.FAVORITE_SUCCESS:
                showToast("收藏成功");
                break;
            case StoryPresenter.FAVORITE_FAILED:
                showToast("收藏失败");
                break;
        }
//        checkIsFavorite();
    }

    @Override
    public void setFavorateCategory(List<String> list) {
        listCategory = list;
        lvChooseCategory.setAdapter(new FavoriteCategoryAdapter());
        lvChooseCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = listCategory.get(i);
                mPresenter.favorite(str, storyId + "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_story, menu);
        return true;
    }

    private EditText etChooseCategory;
    private TextView btnChooseCategorySure;
    private ListView lvChooseCategory;
    private List<String> listCategory;

    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(StoryActivity.this, R.style.BottomSheetDialog);
        View view = LayoutInflater.from(StoryActivity.this).inflate(R.layout.view_choose_category, null);
        etChooseCategory = (EditText) view.findViewById(R.id.et_choose_category);
        btnChooseCategorySure = (TextView) view.findViewById(R.id.tv_choose_category_sure);
        lvChooseCategory = (ListView) view.findViewById(R.id.lv_choose_category);
        mPresenter.getFavorateCategory();
        btnChooseCategorySure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etChooseCategory.getText().toString();
                if (name.equals("")) {
                    showToast("null");
                    return;
                }
                mPresenter.favorite(name, storyId + "");
            }
        });
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.contentView(view)
                .show();
    }

    class FavoriteCategoryAdapter extends BaseAdapter {

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
            view = LayoutInflater.from(StoryActivity.this).inflate(R.layout.item_textview, null, false);
            TextView tv = (TextView) view.findViewById(R.id.tv_normal);
            tv.setText(listCategory.get(i));
            return view;
        }
    }

}
