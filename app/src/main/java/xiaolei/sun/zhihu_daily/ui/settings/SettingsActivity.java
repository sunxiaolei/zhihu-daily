package xiaolei.sun.zhihu_daily.ui.settings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.feedback.FeedbackAgent;

import java.util.List;

import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.utils.AndroidUtils;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class SettingsActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    /**
     * 应用缓存大小
     */
    private TextView tvCacheSize;
    /**
     * 关于
     */
    private TextView tvAbout;
    /**
     * 版本号
     */
    private TextView tvVersion;
    /**
     * 反馈
     */
    private TextView tvFeedback;
    /**
     * 清除缓存
     */
    private RelativeLayout layoutCleanCache;
    /**
     * 检查更新
     */
    private RelativeLayout layoutCheckUpdate;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.set_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvCacheSize = (TextView) findViewById(R.id.tv_setting_cache_size);
        getCacheSize();
        layoutCleanCache = (RelativeLayout) findViewById(R.id.layout_setting_clean_cache);
        layoutCleanCache.setOnClickListener(this);

        tvFeedback = (TextView) findViewById(R.id.tv_setting_feedback);
        tvFeedback.setOnClickListener(this);

        tvAbout = (TextView) findViewById(R.id.tv_setting_about);
        tvAbout.setOnClickListener(this);

        tvVersion = (TextView) findViewById(R.id.tv_setting_version);
        tvVersion.setText(AndroidUtils.getAppVersion(getApplicationContext()));

        layoutCheckUpdate = (RelativeLayout) findViewById(R.id.layout_setting_check_update);
        layoutCheckUpdate.setOnClickListener(this);
    }

    /**
     * 获取缓存大小
     */
    private void getCacheSize() {
        try {
            String size = AndroidUtils.getCacheSize(this.getCacheDir());
            if (size.startsWith("0.0")) {
                tvCacheSize.setText(null);
            } else {
                tvCacheSize.setText(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_setting_clean_cache:
                AndroidUtils.cleanInternalCache(getApplicationContext());
                showDialog(null, "清理成功");
                getCacheSize();
                break;
            case R.id.tv_setting_feedback:
                FeedbackAgent agent = new FeedbackAgent(getApplicationContext());
                agent.startDefaultThreadActivity();
                break;
            case R.id.tv_setting_about:
                showDialog(getString(R.string.set_about), "Zhihu-Daily\n\nsunxiaolei92@gmail.com");
                break;
            case R.id.layout_setting_check_update:
                String newestVersion = AVAnalytics.getConfigParams(getApplicationContext(), Constant.LEAN_CLOUD_PARAMS_NEWESTVERSION_KEY);
                if (!AndroidUtils.getAppVersion(getApplicationContext()).equals(newestVersion)) {
                    showDialog("提示", "有更新\n最新版本:" + newestVersion, getString(R.string.download),
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    getApkUrl();
                                }
                            });
                }
                break;
        }
    }

    private void getApkUrl() {
        AVQuery<AVObject> query = new AVQuery<>("_File");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                String url = (String) list.get(0).get("url");
                if (url != null) {
                    Intent intent = new Intent(SettingsActivity.this, UpdateService.class);
                    intent.putExtra("DOWNLOAD_URL", url);
                    startService(intent);
                }
            }
        });
    }

}
