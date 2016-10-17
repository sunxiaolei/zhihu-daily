package xiaolei.sun.zhihu_daily.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseActivity<T extends IPresenter> extends RxLifeActivity implements IView,View.OnClickListener{

    protected T mPresenter;

    private MaterialDialog mDialog;
    private MaterialDialog mLoading;

    protected abstract T createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
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
        mLoading = new MaterialDialog.Builder(BaseActivity.this)
                .content("Loading")
                .progress(true, 0)
                .theme(Theme.LIGHT)
                .cancelable(false)
                .show();
        mLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mLoading = null;
            }
        });
    }

    public void showDialog(String title, String msg) {
        mDialog = new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .title(title)
                .content(msg)
                .cancelable(false)
                .positiveText("确定")
                .show();
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mDialog = null;
            }
        });
    }

    public void dismissDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
            mLoading = null;
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
