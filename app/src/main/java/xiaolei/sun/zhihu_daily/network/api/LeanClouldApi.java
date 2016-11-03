package xiaolei.sun.zhihu_daily.network.api;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRelationResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.FavoriteResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterResponse;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public interface LeanClouldApi {

    /***********************************************************************************************
     * 注册
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("users")
    Observable<RegisterResponse> doRegister(@Body LoginRequest register);

    /***********************************************************************************************
     * 登录
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("login")
    Observable<LoginResponse> doLogin(@Body LoginRequest login);

    /***********************************************************************************************
     * 收藏
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("classes/Favorite")
    Observable<FavoriteResponse> doFavorite(@Body FavoriteRequest request);

    /***********************************************************************************************
     * 获取收藏列表
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY})
    @GET("classes/Favorite")
    Observable<FavoriteRelationResponse> getFavoriteCategory(@QueryMap Map<String,String> map);
}
