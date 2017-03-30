package xiaolei.sun.zhihu_daily.ui.settings;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.feedback.FeedbackAgent;

import java.util.List;


import butterknife.BindView;
import sunxl8.myutils.AppUtils;
import sunxl8.myutils.CleanUtils;
import sunxl8.myutils.FileUtils;
import sunxl8.myutils.SPUtils;
import xiaolei.sun.zhihu_daily.Constant;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.BaseApplication;
import xiaolei.sun.zhihu_daily.ui.base.BaseSwipeBackActivity;
import xiaolei.sun.zhihu_daily.ui.base.IPresenter;
import xiaolei.sun.zhihu_daily.widget.colorful.Colorful;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class SettingsActivity extends BaseSwipeBackActivity implements View.OnClickListener {

    @BindView(R.id.tv_setting_cache_size)//应用缓存大小
            TextView tvCacheSize;
    @BindView(R.id.tv_setting_about)//关于
            TextView tvAbout;
    @BindView(R.id.tv_setting_version)//版本号
            TextView tvVersion;
    @BindView(R.id.tv_setting_feedback)//反馈
            TextView tvFeedback;
    @BindView(R.id.layout_setting_clean_cache)//清除缓存
            RelativeLayout layoutCleanCache;
    @BindView(R.id.switch_setting_change_theme)//切换主题
            Switch switchTheme;
    @BindView(R.id.layout_setting_check_update)//检查更新
            RelativeLayout layoutCheckUpdate;
    @BindView(R.id.rl_setting_logout)//退出登录
            RelativeLayout layoutLogout;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_setting;
    }

    public Colorful initColorful() {
        return new Colorful.Builder(this)
                .backgroundDrawable(R.id.root_view_settings, R.attr.background_color)
                .backgroundColor(R.id.toolbar, R.attr.colorPrimary)
                .textColor(R.id.tv_setting_clean_cache, R.attr.text_color)
                .textColor(R.id.tv_setting_cache_size, R.attr.text_color)
                .textColor(R.id.tv_setting_change_theme, R.attr.text_color)
                .textColor(R.id.tv_setting_change_pwd, R.attr.text_color)
                .textColor(R.id.tv_setting_check_update, R.attr.text_color)
                .textColor(R.id.tv_setting_version, R.attr.text_color)
                .textColor(R.id.tv_setting_about, R.attr.text_color)
                .textColor(R.id.tv_setting_feedback, R.attr.text_color)
                .create();
    }

    @Override
    public void init() {
        mColorful = initColorful();
        if (SPUtils.getInstance(Constant.SP_THEME).getBoolean(Constant.SP_THEME_NIGHT)) {
            mColorful.setTheme(R.style.AppThemeNight);
        } else {
            mColorful.setTheme(R.style.AppThemeDay);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.set_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCacheSize();
        layoutCleanCache.setOnClickListener(this);

        switchTheme.setChecked(SPUtils.getInstance(Constant.SP_THEME).getBoolean(Constant.SP_THEME_NIGHT, false));
        switchTheme.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mColorful.setTheme(R.style.AppThemeNight);
                SPUtils.getInstance(Constant.SP_THEME).put(Constant.SP_THEME_NIGHT, true);
            } else {
                mColorful.setTheme(R.style.AppThemeDay);
                SPUtils.getInstance(Constant.SP_THEME).put(Constant.SP_THEME_NIGHT, false);
            }

        });

        tvFeedback.setOnClickListener(this);
        tvAbout.setOnClickListener(this);
        tvVersion.setText(AppUtils.getAppVersionName());
        layoutCheckUpdate.setOnClickListener(this);
        layoutLogout.setOnClickListener(this);

    }

    /**
     * 获取缓存大小
     */
    private void getCacheSize() {
        try {
            String size = FileUtils.getDirSize(this.getCacheDir());
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
                CleanUtils.cleanInternalCache();
                showDialog(null, "清理成功");
                getCacheSize();
                break;
            case R.id.tv_setting_feedback:
                FeedbackAgent agent = new FeedbackAgent(getApplicationContext());
                agent.startDefaultThreadActivity();
                break;
            case R.id.tv_setting_about:
                showDialog(getString(R.string.set_about), "zhihu-daily\n\nxiaoleisun92@gmail.com");
                break;
            case R.id.layout_setting_check_update:
                String newestVersion = AVAnalytics.getConfigParams(getApplicationContext(), Constant.LEAN_CLOUD_PARAMS_NEWESTVERSION_KEY);
                String updateInfo = AVAnalytics.getConfigParams(getApplicationContext(), Constant.LEAN_CLOUD_PARAMS_UPDATEINFO_KEY);
                if (!AppUtils.getAppVersionName().equals(newestVersion)) {
                    showDialog("提示", "有更新\n最新版本:" + newestVersion + "\n" + updateInfo, getString(R.string.download),
                            (dialogInterface, i) -> getApkUrl());
                } else {
                    showDialog("提示", "当前已是最新版本");
                }
                break;
            case R.id.rl_setting_logout:
                showDialog("确认退出", null, "确定", (dialogInterface, i) -> {
                    SPUtils.getInstance(Constant.SP_USER).clear();
                    BaseApplication.isLogin = false;
                });
                break;
        }
    }

    private void getApkUrl() {
        AVQuery<AVObject> query = new AVQuery<>("_File");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size() > 0) {
                    String url = (String) list.get(list.size() - 1).get("url");
                    if (url != null) {
                        Intent intent = new Intent(SettingsActivity.this, UpdateService.class);
                        intent.putExtra("DOWNLOAD_URL", url);
                        startService(intent);
                    }
                }
            }
        });
    }

}
