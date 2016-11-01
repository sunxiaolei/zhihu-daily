package xiaolei.sun.zhihu_daily.network.api;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.BaseLeanCloudResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterRequest;

/**
 * Created by sunxl8 on 2016/11/1.
 */

public interface LeanClouldApi {

    @Headers({"X-LC-Id:" + Constant.LEAN_CLOUD_ID,
            "X-LC-Key:" + Constant.LEAN_CLOUD_KEY,
            "Content-Type:application/json"})
    @POST("/1.1/users")
    Observable<BaseLeanCloudResponse> doRegister(@Body RegisterRequest register);
}
