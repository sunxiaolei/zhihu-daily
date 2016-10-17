package xiaolei.sun.zhihu_daily.ui.login;

import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.ui.base.IView;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginContract {

    interface View extends IView{

        void showRegisterSheet();

        void showResult(String title,String msg);

        void gotoMain();

    }

    interface Presenter extends IPresenter<View>{

        void login(String phone,String password);

        void register(String phone,String password);

    }
}
