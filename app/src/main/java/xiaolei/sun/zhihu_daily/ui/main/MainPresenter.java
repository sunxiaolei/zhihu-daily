package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsDate;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsLasted;
import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.network.entity.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.TopStoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.UserBean;
import xiaolei.sun.zhihu_daily.ui.SplashActivity;
import xiaolei.sun.zhihu_daily.utils.SPUtils;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private Context mContext;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        this.mContext = (Context) view;
    }

    @Override
    public void login() {
        SPUtils sp = new SPUtils(mContext, Constant.SP_USER);
        final String username = sp.getString(Constant.SP_USER_NAME);
        String password = sp.getString(Constant.SP_USER_PASSWORD);
        if (username != null && password != null) {
            BmobQuery<UserBean> queryPhone = new BmobQuery<UserBean>();
            BmobQuery<UserBean> queryPassword = new BmobQuery<UserBean>();
            queryPhone.addWhereEqualTo("phone", username);
            queryPassword.addWhereEqualTo("password", password);
            List<BmobQuery<UserBean>> queryList = new ArrayList<>();
            queryList.add(queryPhone);
            queryList.add(queryPassword);
            BmobQuery<UserBean> query = new BmobQuery<>();
            query.and(queryList);
            query.findObjects(new FindListener<UserBean>() {
                @Override
                public void done(List<UserBean> list, BmobException e) {
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
            SPUtils sp = new SPUtils(mContext, Constant.SP_USER);
            mView.setDrawer(sp.getString(Constant.SP_USER_NAME));
        } else {
            mView.setDrawer(mContext.getResources().getString(R.string.click_login));
        }
    }
}
