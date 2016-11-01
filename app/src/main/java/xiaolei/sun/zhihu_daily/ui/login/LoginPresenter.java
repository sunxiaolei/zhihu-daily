package xiaolei.sun.zhihu_daily.ui.login;


import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.network.LeanCloudManager;
import xiaolei.sun.zhihu_daily.network.LeanCloudRequest;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.BaseLeanCloudResponse;
import xiaolei.sun.zhihu_daily.network.entity.leancloud.RegisterRequest;
import xiaolei.sun.zhihu_daily.ui.base.RxPresenter;

/**
 * Created by sunxl8 on 2016/9/26.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {
        AVQuery<AVObject> queryUsername = new AVQuery<>(Constant.LEAN_CLOUD_TABLE_USER);
        queryUsername.whereEqualTo("username", username);
        AVQuery<AVObject> queryPassword = new AVQuery<>(Constant.LEAN_CLOUD_TABLE_USER);
        queryUsername.whereEqualTo("password", password);
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(queryUsername, queryPassword));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mView.showResult("登录", list.get(0).getString("username"));
                    } else {
                        mView.showResult("登录", "用户名或密码不正确");
                    }
                } else {
                    mView.showResult("登录", e.getMessage());
                }
            }
        });
    }

    @Override
    public void register(final String username, final String password) {
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

        RegisterRequest request = new RegisterRequest();
        request.setUsername(username);
        request.setPassword(password);
        LeanCloudRequest.doRegister(request, new Subscriber<BaseLeanCloudResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.showResult("注册", e.toString());
            }

            @Override
            public void onNext(BaseLeanCloudResponse baseLeanCloudResponse) {
                mView.showResult("注册", baseLeanCloudResponse.getCode() + baseLeanCloudResponse.getError());
            }
        });

    }

}
