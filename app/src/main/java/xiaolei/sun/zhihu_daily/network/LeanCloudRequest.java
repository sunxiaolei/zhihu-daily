package xiaolei.sun.zhihu_daily.network;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.api.LeanClouldApi;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterResponse;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public class LeanCloudRequest {

    /**
     * 注册
     *
     * @param request
     * @return
     */
    public static Observable<RegisterResponse> doRegister(LoginRequest request) {
        return LeanCloudManager.getCommonClient()
                .create(LeanClouldApi.class)
                .doRegister(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录
     *
     * @param request
     * @return
     */
    public static Observable<LoginResponse> doLogin(LoginRequest request) {
        return LeanCloudManager.getCommonClient()
                .create(LeanClouldApi.class)
                .doLogin(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
