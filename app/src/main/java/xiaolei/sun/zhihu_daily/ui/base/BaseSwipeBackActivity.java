package xiaolei.sun.zhihu_daily.ui.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Toast;

import xiaolei.sun.zhihu_daily.widget.swipebacklayout.SwipeBackActivity;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseSwipeBackActivity<T extends IPresenter> extends SwipeBackActivity implements IView{

    protected T mPresenter;

    private AlertDialog mDialog;
    private ProgressDialog mLoading;

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
        mLoading = new ProgressDialog(this);
        mLoading.show();
        mLoading.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mLoading = null;
            }
        });
    }

    public void showDialog(String title, String msg) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton("确定", null)
                .create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                mDialog = null;
            }
        });
    }

    public void showDialog(String title, String msg, String positiveText, DialogInterface.OnClickListener listener) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(positiveText, listener)
                .create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
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
