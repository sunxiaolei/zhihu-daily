package xiaolei.sun.zhihu_daily.ui.story;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;
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
    private AppBarLayout layoutTop;

    private WebView web;

    private StoryPresenter mPresenter;

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
        mPresenter = new StoryPresenter(this);

        btnFavorite = (FloatingActionButton) findViewById(R.id.btn_story_favorite);
        web = (WebView) findViewById(R.id.web_activity_story);
        image = (SimpleDraweeView) findViewById(R.id.img_activity_story);

        showLoading();
        mPresenter.getNews(storyId);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog("选择分类", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        mPresenter.favorite("input");
                    }
                });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_story, menu);
        return true;
    }
}
