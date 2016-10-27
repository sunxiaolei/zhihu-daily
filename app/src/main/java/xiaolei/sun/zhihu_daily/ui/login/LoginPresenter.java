package xiaolei.sun.zhihu_daily.ui.login;


import java.util.ArrayList;
import java.util.List;

import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.utils.SPUtils;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String phone, String password) {
    }

    @Override
    public void register(final String phone, final String password) {
        //先检测账号是否已注册

    }

}
