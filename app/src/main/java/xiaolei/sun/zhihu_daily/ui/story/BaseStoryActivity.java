package xiaolei.sun.zhihu_daily.ui.story;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.customerview.swipebacklayout.SwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.BaseView;
import xiaolei.sun.zhihu_daily.ui.login.LoginPresenter;

import static android.R.attr.id;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public class BaseStoryActivity extends SwipeBackActivity implements BaseView {

    private MaterialDialog.Builder mDialog;
    private MaterialProgressBar mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void init() {

    }

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(this);
        }
        mDialog.progress(true, 0)
                .show();
    }

    @Override
    public void dismissLoading() {
        if (mDialog != null) {
            mDialog.show().dismiss();
        }
    }

    @Override
    public void showDialog(String title, String msg) {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(this);
        }
        mDialog.theme(Theme.LIGHT);
        mDialog.title(title);
        mDialog.content(msg);
        mDialog.positiveText("确定");
        mDialog.show();

    }

    @Override
    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.show().dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
