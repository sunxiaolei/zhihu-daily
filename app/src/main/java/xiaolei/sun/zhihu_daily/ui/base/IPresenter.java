package xiaolei.sun.zhihu_daily.ui.base;

/**
 * Created by sunxl8 on 2016/10/17.
 */

public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
}
