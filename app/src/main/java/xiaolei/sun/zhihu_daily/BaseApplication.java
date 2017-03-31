package xiaolei.sun.zhihu_daily;

import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;

import sunxl8.myutils.Utils;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;

import static com.orhanobut.logger.Logger.init;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class BaseApplication extends LitePalApplication {

    public static boolean isLogin = false;
    public static String userId;
    public static String sessionToken;
    public static LoginResponse user;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mRefWatcher = LeakCanary.install(this);
        Utils.init(this);
        Fresco.initialize(this);
        AVOSCloud.initialize(this, Constant.LEAN_CLOUD_ID, Constant.LEAN_CLOUD_KEY);
        Logger.init("ZhiHu")
                .methodCount(3)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
    }
}
