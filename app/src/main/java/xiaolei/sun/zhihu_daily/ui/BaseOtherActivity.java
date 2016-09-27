package xiaolei.sun.zhihu_daily.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import xiaolei.sun.zhihu_daily.customerview.swipebacklayout.SwipeBackActivity;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseOtherActivity extends SwipeBackActivity {

    private MaterialDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
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

    public abstract void init();

    public abstract int setContentViewId();

    public void showLoading() {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(BaseOtherActivity.this)
                    .content("Loading")
                    .progress(true, 0)
                    .theme(Theme.LIGHT)
                    .show();
        }
    }

    public void showDialog(String title, String msg) {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(this)
                    .theme(Theme.LIGHT)
                    .title(title)
                    .content(msg)
                    .positiveText("确定")
                    .show();
        }
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
