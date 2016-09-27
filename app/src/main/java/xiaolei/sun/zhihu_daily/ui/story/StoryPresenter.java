package xiaolei.sun.zhihu_daily.ui.story;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.network.api.ApiNews;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class StoryPresenter implements StoryContract.Presenter{

    private ApiNews api;
    private StoryBean bean;

    private StoryContract.View mView;

    public StoryPresenter(StoryContract.View view) {
        this.mView = view;
    }

    @Override
    public void getNews(int storyId) {
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
                mView.setImageUrl(bean.getImage());
                mView.setTitle(storyBean.getSection().getName());
            }
        });

    }

    @Override
    public void getCss() {
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
                mView.loadData(adjustCss(string));
//                web.loadData(adjustCss(string), "text/html; charset=UTF-8", null);
            }
        });
    }

    public String adjustCss(String string) {
        String str = string.replace("height: 200px;","height: 0px;");
        str = str.replace("margin: 20px 0;","margin: 50px 0 20px;");
        return "<style type=\"text/css\">" + str + "</style>" + bean.getBody();
    }
}
