package xiaolei.sun.zhihu_daily.network.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StartImageBean;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StoryBean;

/**
 * Created by sunxl8 on 2017/3/27.
 */

public interface ZhihuDailyApi {

    @GET("api/4/news/{id}")
    Observable<StoryBean> getNews(@Path("id") int id);

    @GET("css/news_qa.auto.css")
    Observable<String> getCss(@QueryMap Map<String,String> map);

    @GET("api/4/news/before/{date}")
    Observable<NewsBean> getNewsByDate(@Path("date") String date);

    @GET("api/4/news/latest")
    Observable<NewsBean> getNewsLasted();

    @GET("api/4/start-image/{width}*{height}")
    Observable<StartImageBean> getStartImage(@Path("width") int width, @Path("height") int height);
}
