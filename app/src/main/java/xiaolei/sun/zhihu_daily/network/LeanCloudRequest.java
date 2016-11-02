package xiaolei.sun.zhihu_daily.network;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xiaolei.sun.zhihu_daily.network.api.LeanClouldApi;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteCategoryResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteResponse;
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

    /**
     * 收藏
     *
     * @param request
     * @return
     */
    public static Observable<FavoriteResponse> doFavorite(FavoriteRequest request) {
        return LeanCloudManager.getCommonClient()
                .create(LeanClouldApi.class)
                .doFavorite(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取收藏列表
     *
     * @param filter
     * @return
     */
    public static Observable<FavoriteCategoryResponse> getFavoriteCategory(String filter) {
        return LeanCloudManager.getCommonClient()
                .create(LeanClouldApi.class)
                .getFavoriteCategory(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
