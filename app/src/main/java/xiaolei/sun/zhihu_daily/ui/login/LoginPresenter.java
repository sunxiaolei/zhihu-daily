package xiaolei.sun.zhihu_daily.ui.login;


import rx.Subscriber;
import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.network.LeanCloudManager;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.LoginResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterResponse;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    public static final int LOGIN_STATE_SUCCESS = 0;
    public static final int LOGIN_STATE_FAILED = 1;
    public static final int REGISTER_STATE_SUCCESS = 3;
    public static final int REGISTER_STATE_FAILED = 4;

    @Override
    public void login(String username, String password) {
//        AVQuery<AVObject> queryUsername = new AVQuery<>(Constant.LEAN_CLOUD_TABLE_USER);
//        queryUsername.whereEqualTo("username", username);
//        AVQuery<AVObject> queryPassword = new AVQuery<>(Constant.LEAN_CLOUD_TABLE_USER);
//        queryUsername.whereEqualTo("password", password);
//        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(queryUsername, queryPassword));
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e == null) {
//                    if (list.size() > 0) {
//                        mView.showResult();
//                    } else {
//                        mView.showResult();
//                    }
//                } else {
//                    mView.showResult();
//                }
//            }
//        });

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        LeanCloudRequest.doLogin(request)
                .subscribe(new Subscriber<LoginResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showResult(LOGIN_STATE_FAILED);
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        ZhihuDailyApplication.isLogin = true;
                        ZhihuDailyApplication.user = loginResponse;
                        mView.showResult(LOGIN_STATE_SUCCESS);
                    }
                });

    }

    @Override
    public void register(String username, String password) {
//        AVObject todoFolder = new AVObject(Constant.LEAN_CLOUD_TABLE_USER);
//        todoFolder.put("username", username);
//        todoFolder.put("password", password);
//        todoFolder.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if (e == null) {
//                    mView.showResult("注册成功", null);
//                } else {
//                    mView.showResult("注册失败", e.getMessage());
//                }
//            }
//        });

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        LeanCloudRequest.doRegister(request)
        .subscribe(new Subscriber<RegisterResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showResult(REGISTER_STATE_FAILED);
            }

            @Override
            public void onNext(RegisterResponse response) {
                ZhihuDailyApplication.isLogin = true;
                ZhihuDailyApplication.sessionToken = response.getSessionToken();
                mView.showResult(REGISTER_STATE_SUCCESS);
            }
        });

    }

}
