package xiaolei.sun.zhihu_daily.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.widget.swipebacklayout.SwipeBackActivity;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseSwipeBackActivity<T extends IPresenter> extends SwipeBackActivity implements IView{

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

    public abstract int setContentViewId();

    public abstract void init();

    public void showLoading() {
        mLoading = new MaterialDialog.Builder(BaseSwipeBackActivity.this)
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

    public void showEditDialog(String msg, MaterialDialog.InputCallback callback) {
        mDialog = new MaterialDialog.Builder(this)
                .theme(Theme.LIGHT)
                .content(msg)
                .inputRangeRes(2, 20, R.color.colorPrimary)
                .input(null, null, callback)
                .negativeText("取消")
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mDialog.dismiss();
                    }
                })
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
        dismissDialog();
    }
}