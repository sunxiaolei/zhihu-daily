package xiaolei.sun.zhihu_daily.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ZhihuManager {

    private static volatile ZhihuManager instance;

    private Retrofit retrofit;
    private Retrofit.Builder builder;

    public static ZhihuManager getInstance(int i) {
        if (instance == null) {
            synchronized (ZhihuManager.class) {
                if (instance == null) {
                    instance = new ZhihuManager(i);
                }
            }
        }
        instance = new ZhihuManager(i);
        return instance;
    }

    public static ZhihuManager getInstance() {
        if (instance == null) {
            synchronized (ZhihuManager.class) {
                if (instance == null) {
                    instance = new ZhihuManager(0);
                }
            }
        }
        instance = new ZhihuManager(0);
        return instance;
    }

    private ZhihuManager(int i) {
        init(i);
    }


    public void init(){
        init(0);
    }


    public void init(int i) {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Logger.d("Request==>" + request.toString());
                        long t1 = System.nanoTime();
                        okhttp3.Response response = chain.proceed(chain.request());
                        long t2 = System.nanoTime();
                        Logger.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                        okhttp3.MediaType mediaType = response.body().contentType();
                        String content = response.body().string();
                        Logger.d("Response==>" + content);
                        return response.newBuilder()
                                .body(okhttp3.ResponseBody.create(mediaType, content))
                                .build();
                    }
                })
                .connectTimeout(NetWorkConstant.TIME_OUT, TimeUnit.SECONDS);
        builder = new Retrofit.Builder();
        if (i == 0) {
            builder.addConverterFactory(GsonConverterFactory.create());
        } else {
            builder.addConverterFactory(ScalarsConverterFactory.create());
        }

        retrofit = builder
                .client(client.build())
                .baseUrl(NetWorkConstant.URL_BASE_ZHIHU)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}