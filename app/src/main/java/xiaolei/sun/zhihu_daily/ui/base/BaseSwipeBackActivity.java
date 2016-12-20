package xiaolei.sun.zhihu_daily.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Toast;

import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import butterknife.ButterKnife;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.utils.SPUtils;
import xiaolei.sun.zhihu_daily.widget.colorful.Colorful;
import xiaolei.sun.zhihu_daily.widget.dialog.LoadingDialog;
import xiaolei.sun.zhihu_daily.widget.swipebacklayout.SwipeBackActivity;


/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/21.<br>
 * Email：xiaoleisun92@gmail.com
 */

public abstract class BaseSwipeBackActivity<T extends IPresenter> extends SwipeBackActivity implements IView,LifecycleProvider<ActivityEvent> {

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    protected T mPresenter;

    private AlertDialog mDialog;
    private LoadingDialog mLoading;

    protected abstract T createPresenter();

    protected Colorful mColorful;
    protected SPUtils spTheme;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        mPresenter = createPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
        ButterKnife.bind(this);
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
        mLoading = new LoadingDialog(this);
        mLoading.show();
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
        mDialog.setOnDismissListener(dialogInterface -> mDialog = null);
    }

    public void showDialog(String title, String msg, String positiveText, DialogInterface.OnClickListener listener) {
        mDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton("取消", null)
                .create();
        mDialog.setCanceledOnTouchOutside(false);
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

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        dismissLoading();
        dismissDialog();
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

}
