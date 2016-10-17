package xiaolei.sun.zhihu_daily.ui.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sunxl8 on 2016/10/17.
 *
 * 子类继承这个类,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */

public abstract class RxPresenter<T extends IView> implements IPresenter<T> {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;  //订阅集合

    /**
     * Presenter持有View的引用
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * Presenter和View解绑
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    /**
     * 添加到订阅集合
     */
    protected void addSubscribe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }
    /**
     * 取消订阅
     */
    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }
}

