package xiaolei.sun.zhihu_daily.network;

import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by sunxl8 on 2016/12/21.
 */

public class NetworkManager {

    private static Retrofit commonClient;
    private static Retrofit stringClient;
    private static String lastUrl;

    public static Retrofit getCommonClient(String baseUrl) {
        return getCommonClient(baseUrl, null);
    }

    public static Retrofit getStringClient(String baseUrl) {
        if (stringClient == null) {
            stringClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getHttpClient(null))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return stringClient;
    }

    public static Retrofit getCommonClient(String baseUrl, Map<String, String> headers) {
        if (commonClient == null) {
            lastUrl = baseUrl;
            commonClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getHttpClient(headers))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else if (!lastUrl.equals(baseUrl)) {
            lastUrl = baseUrl;
            commonClient = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(getHttpClient(headers))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return commonClient;
    }

    private static OkHttpClient getHttpClient(final Map<String, String> headers) {

        Interceptor interceptor = chain -> {
            Request request = chain.request();
            Logger.d("Request==>" + request.toString());
            long t1 = System.nanoTime();
            if (headers != null) {
                //可以添加公共参数
//                    HttpUrl hu = request.url().newBuilder()
//                            .addQueryParameter("", "")
//                            .build();
                request.newBuilder()
                        .headers(setHeaders(headers))
                        .build();
            }

            Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Logger.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.code()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Logger.d("Response==>" + content);
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(NetWorkConstant.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(NetWorkConstant.HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(NetWorkConstant.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);

        builder.addInterceptor(interceptor);

        return builder.build();
    }

    private static Headers setHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();

        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
            }
        }
        headers = headersbuilder.build();
        return headers;
    }

}
