package xiaolei.sun.zhihu_daily.ui.main;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import xiaolei.sun.zhihu_daily.network.entity.NewsBean;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class MainContract {

    interface View{

        void setNews(NewsBean newsBean);

        void setDrawer(String name);
    }

    interface Presenter{

        void getNewsLasted();

        void getNewsByDate(CalendarDay date);

        void getUserInfo();

    }
}
