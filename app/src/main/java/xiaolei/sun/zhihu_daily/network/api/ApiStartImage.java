package xiaolei.sun.zhihu_daily.network.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.ZhihuManager;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StartImageBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ApiStartImage {

    private StartImage api;

    public ApiStartImage(){
        api = ZhihuManager.getInstance().create(StartImage.class);
    }

    public void getStartImage(Subscriber<StartImageBean> subscriber){
        api.getStartImage(1080,1920)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private interface StartImage{
        @GET("api/4/start-image/{width}*{height}")
        Observable<StartImageBean> getStartImage(@Path("width") int width, @Path("height") int height);
    }

}
