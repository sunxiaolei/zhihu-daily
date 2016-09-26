package xiaolei.sun.zhihu_daily.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.story.BaseStoryActivity;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/25.<br>
 * Email：xiaoleisun92@gmail.com
 */
public class LoginActivity extends BaseStoryActivity implements LoginContract.View {

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;
    private TextView btnRegister;

    private View registerView;
    private EditText etRegisterName;
    private EditText etRegisterPassword;
    private Button btnRegisterSubmit;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        mPresenter = new LoginPresenter(this);
//        initView();
    }

//    private void initView(){
//        etName = (EditText) findViewById(R.id.et_login_name);
//        etPassword = (EditText) findViewById(R.id.et_login_password);
//        btnLogin = (Button) findViewById(R.id.btn_login_login);
//        btnRegister = (TextView) findViewById(R.id.btn_login_register);
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showRegisterSheet();
//            }
//        });
//    }

    private BottomSheetDialog mBottomSheetDialog;

    @Override
    public void init() {
        etName = (EditText) findViewById(R.id.et_login_name);
        etPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login_login);
        btnRegister = (TextView) findViewById(R.id.btn_login_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterSheet();
            }
        });
    }

    @Override
    public void showRegisterSheet() {
        mBottomSheetDialog = new BottomSheetDialog(LoginActivity.this, R.style.Material_App_BottomSheetDialog);
        registerView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.view_register, null);

        etRegisterName = (EditText) registerView.findViewById(R.id.et_register_name);
        etRegisterPassword = (EditText) registerView.findViewById(R.id.et_register_password);
        btnRegisterSubmit = (Button) registerView.findViewById(R.id.btn_register_submit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etRegisterName.getText().toString();
                String password = etRegisterPassword.getText().toString();
                if (name == null || password == null || name.equals("") || password.equals("")) {
                    showToast("用户名或密码不能为空！");
                    return;
                }
                mPresenter.register(name, password);
            }
        });

        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.contentView(registerView)
                .show();
    }
}

