package xiaolei.sun.zhihu_daily.ui.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.utils.RegexUtils;
import xiaolei.sun.zhihu_daily.widget.colorful.Colorful;
import xiaolei.sun.zhihu_daily.widget.dialog.BottomSheetDialog;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.main.MainActivity;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/25.<br>
 * Email：xiaoleisun92@gmail.com
 */
public class LoginActivity extends BaseSwipeBackActivity<LoginPresenter> implements LoginContract.View {

    private EditText etName;
    private EditText etPassword;
    private TextView btnLogin;
    private TextView btnRegister;

    private View registerView;
    private EditText etRegisterUsername;
    private EditText etRegisterPassword;
    private Button btnRegisterSubmit;

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    public void init() {
        etName = (EditText) findViewById(R.id.et_login_name);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (TextView) findViewById(R.id.tv_login_login);
        btnRegister = (TextView) findViewById(R.id.tv_login_register);

        btnLogin.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String password = etPassword.getText().toString();
            if (name == null || password == null || name.equals("") || password.equals("")) {
                showToast("用户名或密码不能为空！");
                return;
            }
            showLoading();
            mPresenter.login(name, password);
        });

        btnRegister.setOnClickListener(view -> showRegisterSheet());
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public Colorful initColorful() {
        return null;
    }

    @Override
    public void showRegisterSheet() {
        mBottomSheetDialog = new BottomSheetDialog(LoginActivity.this, R.style.BottomSheetDialog);
        registerView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.view_register, null);

        etRegisterUsername = (EditText) registerView.findViewById(R.id.et_register_phone);
        etRegisterPassword = (EditText) registerView.findViewById(R.id.et_register_password);
        btnRegisterSubmit = (Button) registerView.findViewById(R.id.btn_register_submit);

        btnRegisterSubmit.setOnClickListener(view -> {
            String username = etRegisterUsername.getText().toString();
            String password = etRegisterPassword.getText().toString();
            if (username == null || password == null || username.equals("") || password.equals("")) {
                showToast("用户名或密码不能为空！");
                return;
            }
            if (!RegexUtils.isUsername(username)) {
                showToast("用户名只能是2-10位字母数字汉字下划线");
                return;
            }
            showLoading();
            mPresenter.register(username, password);
        });

        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.contentView(registerView)
                .show();
    }

    @Override
    public void showResult(int state) {
        dismissLoading();
        switch (state) {
            case LoginPresenter.LOGIN_STATE_SUCCESS:
            case LoginPresenter.REGISTER_STATE_SUCCESS:
                startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
            case LoginPresenter.LOGIN_STATE_FAILED:
                showDialog("登录失败", null);
                break;
            case LoginPresenter.REGISTER_STATE_FAILED:
                showDialog("注册失败", null);
                break;
        }
    }

}

