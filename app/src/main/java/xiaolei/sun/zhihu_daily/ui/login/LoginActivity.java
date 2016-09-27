package xiaolei.sun.zhihu_daily.ui.login;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.base.BaseOtherActivity;
import xiaolei.sun.zhihu_daily.ui.main.MainActivity;
import xiaolei.sun.zhihu_daily.utils.RegexUtils;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/25.<br>
 * Email：xiaoleisun92@gmail.com
 */
public class LoginActivity extends BaseOtherActivity implements LoginContract.View {

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;
    private TextView btnRegister;

    private View registerView;
    private EditText etRegisterPhone;
    private EditText etRegisterPassword;
    private Button btnRegisterSubmit;

    private LoginPresenter mPresenter;

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    public void init() {
        mPresenter = new LoginPresenter(this);

        etName = (EditText) findViewById(R.id.et_login_name);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login_login);
        btnRegister = (TextView) findViewById(R.id.btn_login_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String password = etPassword.getText().toString();
                if (name == null || password == null || name.equals("") || password.equals("")) {
                    showToast("用户名或密码不能为空！");
                    return;
                }
                showLoading();
                mPresenter.login(name, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterSheet();
            }
        });
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void showRegisterSheet() {
        mBottomSheetDialog = new BottomSheetDialog(LoginActivity.this, R.style.Material_App_BottomSheetDialog);
        registerView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.view_register, null);

        etRegisterPhone = (EditText) registerView.findViewById(R.id.et_register_phone);
        etRegisterPassword = (EditText) registerView.findViewById(R.id.et_register_password);
        btnRegisterSubmit = (Button) registerView.findViewById(R.id.btn_register_submit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etRegisterPhone.getText().toString();
                String password = etRegisterPassword.getText().toString();
                if (phone == null || password == null || phone.equals("") || password.equals("")) {
                    showToast("用户名或密码不能为空！");
                    return;
                }
                if (!RegexUtils.isMobileSimple(phone)){
                    showToast("请输入正确手机号码！");
                    return;
                }
                showLoading();
                mPresenter.register(phone, password);
            }
        });

        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.contentView(registerView)
                .show();
    }

    @Override
    public void showResult(String title, String msg) {
        dismissLoading();
        showDialog(title, msg);
    }

    @Override
    public void gotoMain() {
        startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}

