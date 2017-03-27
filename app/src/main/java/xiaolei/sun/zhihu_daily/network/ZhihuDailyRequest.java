package xiaolei.sun.zhihu_daily.network;

import java.util.Map;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.network.api.ZhihuDailyApi;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StartImageBean;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StoryBean;

/**
 * Created by sunxl8 on 2017/3/27.
 */

public class ZhihuDailyRequest {

    public static void getNews(int id, Subscriber<StoryBean> subscriber) {
        NetworkManager.getCommonClient(NetWorkConstant.URL_BASE_ZHIHU)
                .create(ZhihuDailyApi.class)
                .getNews(id)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(subscriber);
    }

    public static void getCss(Map<String, String> params, Subscriber<String> subscriber) {
        NetworkManager.getStringClient(NetWorkConstant.URL_BASE_ZHIHU)
                .create(ZhihuDailyApi.class)
                .getCss(params)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(subscriber);

    }

    public static void getNewsLasted(String date, Subscriber<NewsBean> subscriber) {
        NetworkManager.getCommonClient(NetWorkConstant.URL_BASE_ZHIHU)
                .create(ZhihuDailyApi.class)
                .getNewsByDate(date)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(subscriber);
    }

    public static void getNewsLasted(Subscriber<NewsBean> subscriber) {
        NetworkManager.getCommonClient(NetWorkConstant.URL_BASE_ZHIHU)
                .create(ZhihuDailyApi.class)
                .getNewsLasted()
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(subscriber);
    }

    public static void getStartImage(Subscriber<StartImageBean> subscriber) {
        NetworkManager.getCommonClient(NetWorkConstant.URL_BASE_ZHIHU)
                .create(ZhihuDailyApi.class)
                .getStartImage(1080, 1920)
                .compose(SchedulersCompat.applyIoSchedulers())
                .subscribe(subscriber);
    }
}
