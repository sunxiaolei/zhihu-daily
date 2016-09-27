package xiaolei.sun.zhihu_daily.ui.login;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginContract {

    interface View{

        void showRegisterSheet();

        void showResult(String title,String msg);

    }

    interface Presenter{

        void login();

        void register(String phone,String password);

    }
}
