package xiaolei.sun.zhihu_daily.network.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.ZhihuManager;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/25.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ApiNewsDate {

    private NewsDate api;

    public ApiNewsDate(){
        api = ZhihuManager.getInstance().create(NewsDate.class);
    }

    public void getNewsLasted(String date,Subscriber<NewsBean> subscriber){
        api.getNewsByDate(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private interface NewsDate{
        @GET("api/4/news/before/{date}")
        Observable<NewsBean> getNewsByDate(@Path("date") String date);
    }

}
