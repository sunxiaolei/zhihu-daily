package xiaolei.sun.zhihu_daily.ui;

/**
 * Created by sunxl8 on 2016/9/27.
 */

public class BasePresenter<T> {

    protected T mView;

    public BasePresenter(T view){
        this.mView = view;
    }


}
