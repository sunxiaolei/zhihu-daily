package xiaolei.sun.zhihu_daily;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import xiaolei.sun.zhihu_daily.network.entity.BmobUserBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class ZhihuDailyApplication extends Application {

    public static boolean isLogin = false;
    public static String userId;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        AVOSCloud.initialize(this,"1easqLxsHBENYgL5NtmNDCjE-gzGzoHsz","w9D9S3qFO973qntytibgHec6");

        Logger
                .init("ZhiHu")
                .methodCount(3)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
    }
}
