package xiaolei.sun.zhihu_daily.ui.login;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import xiaolei.sun.zhihu_daily.network.entity.UserBean;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login() {
        if (true) {

        }
    }

    @Override
    public void register(String phone, String password) {
        UserBean user = new UserBean();
        user.setPassword(password);
        user.setPhone(phone);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    view.showResult("提示", "注册成功");
                } else {
                    view.showResult("提示", "创建数据失败：" + e.getMessage());
                }
            }
        });
    }
}
