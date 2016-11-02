package xiaolei.sun.zhihu_daily.network.api;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterResponse;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public interface LeanClouldApi {

    /***********************************************************************************************
     * register
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("/1.1/users")
    Observable<RegisterResponse> doRegister(@Body LoginRequest register);

    /***********************************************************************************************
     * login
     **********************************************************************************************/
    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("/1.1/login")
    Observable<LoginResponse> doLogin(@Body LoginRequest login);
}
