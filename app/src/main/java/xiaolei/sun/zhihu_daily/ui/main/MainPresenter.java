package xiaolei.sun.zhihu_daily.ui.main;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import rx.Subscriber;
import sunxl8.myutils.SPUtils;
import sunxl8.myutils.TimeUtils;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.BaseApplication;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.ZhihuDailyRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void login() {
        final String username = SPUtils.getInstance(Constant.SP_USER).getString(Constant.SP_USER_NAME);
        final String password = SPUtils.getInstance(Constant.SP_USER).getString(Constant.SP_USER_PASSWORD);
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        LeanCloudRequest.doLogin(request)
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setDrawer(BaseApplication.getContext().getResources().getString(R.string.click_login));
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        BaseApplication.isLogin = true;
                        BaseApplication.user = loginResponse;
                        mView.setDrawer(username);
                    }
                });
    }

    @Override
    public void getNewsLasted() {
        ZhihuDailyRequest.getNewsLasted(new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(NewsBean newsLastedBean) {
                mView.setNews(newsLastedBean);
            }
        });
    }

    @Override
    public void getNewsByDate(CalendarDay date) {
        //20131119
        String dateFomat = TimeUtils.date2String(date.getDate(), "yyyyMMdd");
        ZhihuDailyRequest.getNewsLasted(dateFomat, new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewsBean newsBean) {
                mView.setNews(newsBean);
            }
        });
    }

    @Override
    public void getUserInfo() {
        if (BaseApplication.isLogin) {
            mView.setDrawer(SPUtils.getInstance(Constant.SP_USER).getString(Constant.SP_USER_NAME));
        } else {
            mView.setDrawer(BaseApplication.getContext().getResources().getString(R.string.click_login));
        }
    }
}
