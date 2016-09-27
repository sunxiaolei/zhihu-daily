package xiaolei.sun.zhihu_daily.ui.login;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginContract {

    interface View{

        void showRegisterSheet();

        void showResult(String title,String msg);

        void gotoMain();

    }

    interface Presenter{

        void login(String phone,String password);

        void register(String phone,String password);

    }
}
