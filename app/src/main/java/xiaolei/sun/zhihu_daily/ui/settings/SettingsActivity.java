package xiaolei.sun.zhihu_daily.ui.settings;

import android.support.v7.widget.Toolbar;

import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.ui.base.BaseOtherActivity;

/**
 * Created by sunxl8 on 2016/9/29.
 */

public class SettingsActivity extends BaseOtherActivity {

    @Override
    public int setContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.set_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
