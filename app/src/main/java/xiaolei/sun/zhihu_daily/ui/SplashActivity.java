package xiaolei.sun.zhihu_daily.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import xiaolei.sun.zhihu_daily.network.ZhihuDailyRequest;
import xiaolei.sun.zhihu_daily.ui.main.MainActivity;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StartImageBean;
import xiaolei.sun.zhihu_daily.utils.AndroidUtils;

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

        if (!AndroidUtils.isConnected(this)) {
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
            Observable.timer(1, TimeUnit.SECONDS)
                    .subscribe(aLong -> {
                        SplashActivity.this.finish();
                    });
        }

        img = (SimpleDraweeView) findViewById(R.id.img_activity_splash);
        tvCopyright = (TextView) findViewById(R.id.tv_activity_splash);

        ZhihuDailyRequest.getStartImage(new Subscriber<StartImageBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                tvCopyright.setText("图片加载错误");
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        });
            }

            @Override
            public void onNext(StartImageBean entityStartImage) {
                img.setImageURI(entityStartImage.getImg());
                tvCopyright.setText("@" + entityStartImage.getText());
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        });
            }
        });
    }

}
