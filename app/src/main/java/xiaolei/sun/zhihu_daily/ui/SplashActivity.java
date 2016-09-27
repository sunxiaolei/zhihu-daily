package xiaolei.sun.zhihu_daily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.concurrent.TimeUnit;

import cn.bmob.v3.Bmob;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.ui.base.BaseActivity;
import xiaolei.sun.zhihu_daily.ui.main.MainActivity;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsLasted;
import xiaolei.sun.zhihu_daily.network.api.ApiStartImage;
import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.network.entity.StartImageBean;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class SplashActivity extends AppCompatActivity {

    private SimpleDraweeView img;
    private TextView tvCopyright;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initBmob();

        img = (SimpleDraweeView) findViewById(R.id.img_activity_splash);
        tvCopyright = (TextView) findViewById(R.id.tv_activity_splash);

        ApiStartImage apiStartImage = new ApiStartImage();
        apiStartImage.getStartImage(new Subscriber<StartImageBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(StartImageBean entityStartImage) {
                img.setImageURI(entityStartImage.getImg());
                tvCopyright.setText("@" + entityStartImage.getText());
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            }
                        });
            }
        });
    }

    private void initBmob(){

        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, Constant.BMOB_ID);

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
}
