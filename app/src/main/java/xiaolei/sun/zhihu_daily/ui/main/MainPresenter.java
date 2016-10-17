package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Subscriber;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsDate;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsLasted;
import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.network.entity.BmobUserBean;
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
        if (username != null && password != null) {
            BmobQuery<BmobUserBean> queryPhone = new BmobQuery<BmobUserBean>();
            BmobQuery<BmobUserBean> queryPassword = new BmobQuery<BmobUserBean>();
            queryPhone.addWhereEqualTo("phone", username);
            queryPassword.addWhereEqualTo("password", password);
            List<BmobQuery<BmobUserBean>> queryList = new ArrayList<>();
            queryList.add(queryPhone);
            queryList.add(queryPassword);
            BmobQuery<BmobUserBean> query = new BmobQuery<>();
            query.and(queryList);
            query.findObjects(new FindListener<BmobUserBean>() {
                @Override
                public void done(List<BmobUserBean> list, BmobException e) {
                    if (e == null) {
                        if (list.size() > 0) {
                            System.out.println(list.get(0));
                            ZhihuDailyApplication.isLogin = true;
                            mView.setDrawer(username);
                        }
                    }
                }
            });
        }
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
