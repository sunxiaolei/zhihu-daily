package xiaolei.sun.zhihu_daily.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import xiaolei.sun.zhihu_daily.widget.dialog.LoadingDialog;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseActivity<T extends IPresenter> extends RxLifeActivity implements IView, View.OnClickListener {

    protected T mPresenter;

    private AlertDialog mDialog;
    private LoadingDialog mLoading;

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
        mLoading = new LoadingDialog(this);
        mLoading.show();
    }

    public void showDialog(String title, String msg) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("确定", null)
                .create();
        mDialog.show();
        mDialog.setOnDismissListener(dialogInterface -> mDialog = null);
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
