package xiaolei.sun.zhihu_daily.ui.story;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.bmob.v3.exception.BmobException;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.base.BaseOtherActivity;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/22.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoryActivity extends BaseOtherActivity implements StoryContract.View {

    private int storyId;

    private FloatingActionButton btnFavorite;
    private SimpleDraweeView image;
    private CollapsingToolbarLayout collapsingToolbar;

    private WebView web;

    private StoryPresenter mPresenter;

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        storyId = getIntent().getIntExtra("STORY_ID", 0);
        mPresenter = new StoryPresenter(this);

        btnFavorite = (FloatingActionButton) findViewById(R.id.btn_story_favorite);
        web = (WebView) findViewById(R.id.web_activity_story);
        image = (SimpleDraweeView) findViewById(R.id.img_activity_story);

        showLoading();
        mPresenter.getNews(storyId);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.favorite();
            }
        });
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
        collapsingToolbar.setTitle(string);
    }

    @Override
    public void loadData(String string) {
        dismissLoading();
        web.loadData(string, "text/html; charset=UTF-8", null);
    }

    @Override
    public void favorite(String message) {
        showToast(message);
    }
}
