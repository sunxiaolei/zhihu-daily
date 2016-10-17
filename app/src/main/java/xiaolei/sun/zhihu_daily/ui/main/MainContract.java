package xiaolei.sun.zhihu_daily.ui.main;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.ui.base.IView;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainContract {

    interface View extends IView{

        void setNews(NewsBean newsBean);

        void setDrawer(String name);
    }

    interface Presenter extends IPresenter<View>{

        void login();

        void getNewsLasted();

        void getNewsByDate(CalendarDay date);

        void getUserInfo();

    }
}
