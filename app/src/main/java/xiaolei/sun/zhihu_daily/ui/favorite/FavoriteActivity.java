package xiaolei.sun.zhihu_daily.ui.favorite;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.facebook.drawee.view.SimpleDraweeView;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.db.bean.DbStory;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class FavoriteActivity extends BaseSwipeBackActivity<FavoritPresenter> implements FavoriteContract.View {

    private String storyId;

    private SimpleDraweeView image;
    private WebView web;

    @Override
    protected FavoritPresenter createPresenter() {
        return new FavoritPresenter();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_favorite;
    }

    @Override
    public void init() {

        storyId = getIntent().getStringExtra("STORY_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_share:
                    break;
                case R.id.action_settings:
                    break;
            }
            return true;
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppBarLayout layoutTop = (AppBarLayout) findViewById(R.id.appbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        layoutTop.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

            }
        });


        web = (WebView) findViewById(R.id.web_favorite);
        image = (SimpleDraweeView) findViewById(R.id.img_favorite);

        showLoading();
        mPresenter.getStory(storyId);
    }

    @Override
    public void setStory(DbStory story) {
        dismissLoading();
        if (story != null) {
            web.loadData(story.getBody(), "text/html; charset=UTF-8", null);
            image.setImageURI(story.getImage());
        }
    }
}
