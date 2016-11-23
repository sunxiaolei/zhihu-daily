package xiaolei.sun.zhihu_daily.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.widget.LoadingView;

/**
 * Created by sunxl8 on 2016/11/23.
 */

public class LoadingDialog extends Dialog {

    private Context mContext;
    private LoadingView loading;

    public LoadingDialog(Context context) {
        super(context, R.style.DialogNoBackground);
        this.mContext = context;
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        loading = (LoadingView) view.findViewById(R.id.loading_view);
        loading.showLoading();
        setContentView(view);
    }

    @Override
    public void dismiss() {
        if (loading != null) {
            loading.stopLoading();
        }
        super.dismiss();
    }
}
