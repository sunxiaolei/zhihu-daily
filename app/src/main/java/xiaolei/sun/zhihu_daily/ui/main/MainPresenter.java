package xiaolei.sun.zhihu_daily.ui.main;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsDate;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsLasted;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.utils.SPUtils;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    @Override
    public void login() {
        SPUtils sp = new SPUtils(ZhihuDailyApplication.getContext(), Constant.SP_USER);
        final String username = sp.getString(Constant.SP_USER_NAME);
        String password = sp.getString(Constant.SP_USER_PASSWORD);
    }

    @Override
    public void getNewsLasted() {
        ApiNewsLasted api = new ApiNewsLasted();
        api.getNewsLasted(new Subscriber<NewsBean>() {
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
        int month = date.getMonth() + 1;
        String strMonth = month + "";
        if (month < 10) {
            strMonth = "0" + month;
        }
        int day = date.getDay() + 1;
        String dateFomat = date.getYear() + strMonth + day;
        ApiNewsDate api = new ApiNewsDate();
        api.getNewsLasted(dateFomat, new Subscriber<NewsBean>() {
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
