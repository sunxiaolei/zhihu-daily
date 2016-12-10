package xiaolei.sun.zhihu_daily;

import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.litepal.LitePalApplication;

import io.rong.imkit.RongIM;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;

import static com.orhanobut.logger.Logger.init;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ZhihuDailyApplication extends LitePalApplication {

    public static boolean isLogin = false;
    public static String userId;
    public static String sessionToken;
    public static LoginResponse user;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        Fresco.initialize(this);
        AVOSCloud.initialize(this, Constant.LEAN_CLOUD_ID, Constant.LEAN_CLOUD_KEY);
        RongIM.init(this);

        Logger
                .init("ZhiHu")
                .methodCount(3)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
    }
}
