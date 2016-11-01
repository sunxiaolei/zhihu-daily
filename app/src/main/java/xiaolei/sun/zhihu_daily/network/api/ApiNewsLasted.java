package xiaolei.sun.zhihu_daily.network.api;

import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.ZhihuManager;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ApiNewsLasted {

    private NewsLasted api;

    public ApiNewsLasted(){
        api = ZhihuManager.getInstance().create(NewsLasted.class);
    }

    public void getNewsLasted(Subscriber<NewsBean> subscriber){
        api.getNewsLasted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private interface NewsLasted{
        @GET("api/4/news/latest")
        Observable<NewsBean> getNewsLasted();
    }

}
