package xiaolei.sun.zhihu_daily.network.api;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.ApiRequest;
import xiaolei.sun.zhihu_daily.network.entity.StoryBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ApiNews {

    private News api;

    public ApiNews(){
        api = ApiRequest.getInstance().create(News.class);
    }

    public void getNews(int id,Subscriber<StoryBean> subscriber){
        api.getNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCss(Map<String,String> params,Subscriber<String> subscriber){
        Css apiCss = ApiRequest.getInstance(1).create(Css.class);
        apiCss.getCss(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private interface News{
        @GET("api/4/news/{id}")
        Observable<StoryBean> getNews(@Path("id") int id);
    }

    private interface Css{
        @GET("css/news_qa.auto.css")
        Observable<String> getCss(@QueryMap Map<String,String> map);
    }

}
