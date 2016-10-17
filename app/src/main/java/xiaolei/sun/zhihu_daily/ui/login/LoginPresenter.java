package xiaolei.sun.zhihu_daily.ui.login;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.entity.BmobUserBean;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;
import xiaolei.sun.zhihu_daily.utils.SPUtils;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String phone, String password) {
        BmobQuery<BmobUserBean> queryPhone = new BmobQuery<BmobUserBean>();
        BmobQuery<BmobUserBean> queryPassword = new BmobQuery<BmobUserBean>();
        queryPhone.addWhereEqualTo("phone", phone);
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
                        ZhihuDailyApplication.isLogin = true;
                        ZhihuDailyApplication.userId = list.get(0).getObjectId();
                        SPUtils sp = new SPUtils(ZhihuDailyApplication.getContext(), Constant.SP_USER);
                        sp.putString(Constant.SP_USER_NAME, list.get(0).getPhone());
                        sp.putString(Constant.SP_USER_PASSWORD, list.get(0).getPassword());
                        mView.gotoMain();
                    } else {
                        mView.showResult("提示", "登录失败：账号或密码不正确");
                    }
                } else {
                    mView.showResult("提示", "登录失败：" + e.getMessage());
                }
            }
        });
    }

    @Override
    public void register(final String phone, final String password) {
        //先检测账号是否已注册
        BmobQuery<BmobUserBean> queryPhone = new BmobQuery<BmobUserBean>();
        queryPhone.addWhereEqualTo("phone", phone);
        queryPhone.findObjects(new FindListener<BmobUserBean>() {
            @Override
            public void done(List<BmobUserBean> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mView.showResult("提示", "账号已注册");
                    } else {
                        BmobUserBean user = new BmobUserBean();
                        user.setPassword(password);
                        user.setPhone(phone);
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    ZhihuDailyApplication.isLogin = true;
                                    ZhihuDailyApplication.userId = objectId;
                                    SPUtils sp = new SPUtils(ZhihuDailyApplication.getContext(), Constant.SP_USER);
                                    sp.putString(Constant.SP_USER_NAME, phone);
                                    sp.putString(Constant.SP_USER_PASSWORD, password);
                                    mView.gotoMain();
                                } else {
                                    mView.showResult("提示", "创建数据失败：" + e.getMessage());
                                }
                            }
                        });
                    }
                } else {
                    mView.showResult("提示", "注册失败：" + e.getMessage());
                }
            }
        });

    }

}
