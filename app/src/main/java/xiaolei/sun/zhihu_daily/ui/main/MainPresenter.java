package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.ZhihuDailyRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.utils.DateUtils;
import xiaolei.sun.zhihu_daily.utils.SPUtils;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void login() {
        SPUtils sp = new SPUtils(ZhihuDailyApplication.getContext(), Constant.SP_USER);
        final String username = sp.getString(Constant.SP_USER_NAME);
        final String password = sp.getString(Constant.SP_USER_PASSWORD);
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
                        mView.setDrawer(ZhihuDailyApplication.getContext().getResources().getString(R.string.click_login));
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        ZhihuDailyApplication.isLogin = true;
                        ZhihuDailyApplication.user = loginResponse;
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
        String dateFomat = DateUtils.getFormatDate(date.getDate(), "yyyyMMdd");
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
        if (ZhihuDailyApplication.isLogin) {
            SPUtils sp = new SPUtils(ZhihuDailyApplication.getContext(), Constant.SP_USER);
            mView.setDrawer(sp.getString(Constant.SP_USER_NAME));
        } else {
            mView.setDrawer(ZhihuDailyApplication.getContext().getResources().getString(R.string.click_login));
        }
    }
}
