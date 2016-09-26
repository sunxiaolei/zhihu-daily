package xiaolei.sun.zhihu_daily.ui;

/**
 * Description: <br>
 * author: sun<br>
 * date: 2016/9/26.<br>
 * Email：xiaoleisun92@gmail.com
 */

public interface BaseView {

    void init();

    void showLoading();

    void dismissLoading();

    void showDialog(String title, String msg);

    void dismissDialog();

    void showToast(String msg);

}
