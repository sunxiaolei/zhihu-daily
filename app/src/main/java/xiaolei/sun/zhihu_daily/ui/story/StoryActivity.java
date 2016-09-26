package xiaolei.sun.zhihu_daily.ui.story;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import xiaolei.sun.zhihu_daily.network.api.ApiNews;
import xiaolei.sun.zhihu_daily.network.entity.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;
import xiaolei.sun.zhihu_daily.ui.BaseActivity;
import xiaolei.sun.zhihu_daily.R;

import static xiaolei.sun.zhihu_daily.R.id.img_activity_story;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/22.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class StoryActivity extends BaseStoryActivity {

    private SimpleDraweeView image;
    private CollapsingToolbarLayout collapsingToolbar;

    private WebView web;

    private ApiNews api;
    private StoryBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

//        StoriesBean story = (StoriesBean) getIntent().getSerializableExtra("STORY");
        int storyId = getIntent().getIntExtra("STORY_ID",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        web = (WebView) findViewById(R.id.web_activity_story);
        image = (SimpleDraweeView) findViewById(R.id.img_activity_story);

        api = new ApiNews();
        api.getNews(storyId, new Subscriber<StoryBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError" + e.toString());
            }

            @Override
            public void onNext(StoryBean storyBean) {
                bean = storyBean;
                getCss();
                image.setImageURI(bean.getImage());
                collapsingToolbar.setTitle(storyBean.getSection().getName());
            }
        });
    }

    private void getCss() {
        String[] strs = bean.getCss().get(0).split("[?]");
        String[] strs2 = strs[1].split("=");
        Map<String, String> params = new HashMap<>();
        params.put(strs2[0], strs2[1]);
        api.getCss(params, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onError" + e.toString());
            }

            @Override
            public void onNext(String string) {
                Logger.d("onNext");
                web.loadData(adjustCss(string), "text/html; charset=UTF-8", null);
            }
        });
    }

    private String adjustCss(String string){
        String str = string.replace("height: 200px;","height: 0px;");
        str = str.replace("margin: 20px 0;","margin: 50px 0 20px;");
        return "<style type=\"text/css\">" + str + "</style>" + bean.getBody();
    }
}
