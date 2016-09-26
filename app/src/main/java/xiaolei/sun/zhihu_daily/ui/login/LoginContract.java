package xiaolei.sun.zhihu_daily.ui.login;


import xiaolei.sun.zhihu_daily.ui.BasePresenter;
import xiaolei.sun.zhihu_daily.ui.BaseView;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginContract {

    interface View extends BaseView {

        void showRegisterSheet();

    }

    interface Presenter extends BasePresenter {

        void login();

        void register(String phone,String password);

    }
}
