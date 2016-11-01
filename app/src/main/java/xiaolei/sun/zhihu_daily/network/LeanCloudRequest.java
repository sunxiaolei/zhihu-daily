package xiaolei.sun.zhihu_daily.network;

import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.api.LeanClouldApi;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.BaseLeanCloudResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterRequest;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public class LeanCloudRequest {

    public static void doRegister(RegisterRequest request, Subscriber<BaseLeanCloudResponse> subscriber) {
        LeanCloudManager.getCommonClient()
                .create(LeanClouldApi.class)
                .doRegister(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
