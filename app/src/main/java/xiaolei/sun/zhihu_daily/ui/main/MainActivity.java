package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import xiaolei.sun.zhihu_daily.BaseApplication;
import xiaolei.sun.zhihu_daily.widget.colorful.Colorful;
import xiaolei.sun.zhihu_daily.widget.dialog.BottomSheetDialog;
import xiaolei.sun.zhihu_daily.widget.quickadapter.SpringView;
import xiaolei.sun.zhihu_daily.widget.quickadapter.container.RotationFooter;
import xiaolei.sun.zhihu_daily.widget.quickadapter.container.RotationHeader;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.TopStoriesBean;
import xiaolei.sun.zhihu_daily.ui.base.BaseActivity;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.entity.zhihu.NewsBean;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteListActivity;
import xiaolei.sun.zhihu_daily.ui.login.LoginActivity;
import xiaolei.sun.zhihu_daily.ui.settings.SettingsActivity;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener, MainContract.View {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StoriesRecyclerAdapter adapter;
    private FloatingActionButton btnCalendar;
    private SpringView refreshLayout;

    private BottomSheetDialog mBottomSheetDialog;

    private List<StoriesBean> listStories;
    private List<TopStoriesBean> listTop;

    //Drawer
    private SimpleDraweeView ivHead;
    private TextView tvName;
    private TextView tvSet;
    private TextView tvFavorite;

    private Colorful mColorful;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    private Colorful initColorful() {
        return new Colorful.Builder(this)
                .backgroundColor(R.id.root_view_main, R.attr.background_color)
                .backgroundColor(R.id.toolbar, R.attr.colorPrimary)
                .textColor(R.id.tv_setting_clean_cache, R.attr.text_color)
                .create();
    }

    @Override
    public void init() {
        mPresenter.login();
        mColorful = initColorful();
//        spTheme = new SPUtils(this, Constant.SP_THEME);
//        if (spTheme.getBoolean(Constant.SP_THEME_NIGHT)) {
//            mColorful.setTheme(R.style.AppThemeNight);
//        } else {
//            mColorful.setTheme(R.style.AppThemeDay);
//        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationOnClickListener(v -> {

            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                drawerLayout.openDrawer(GravityCompat.START);
        });

        btnCalendar = (FloatingActionButton) findViewById(R.id.btn_main_calendar);
        btnCalendar.setOnClickListener(view -> showBottomSheet());

        refreshLayout = (SpringView) findViewById(R.id.refresh_main);

        refreshLayout.setHeader(new RotationHeader(this));
        refreshLayout.setFooter(new RotationFooter(this));
        refreshLayout.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNewsLasted();
            }

            @Override
            public void onLoadmore() {
                showToast("no more");
                refreshLayout.onFinishFreshAndLoad();
            }
        });

        mPresenter.getNewsLasted();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new StoriesItemDecoration());

        //Drawer
        ivHead = (SimpleDraweeView) findViewById(R.id.ic_drawer_head);
        tvName = (TextView) findViewById(R.id.tv_drawer_name);
        tvSet = (TextView) findViewById(R.id.tv_drawer_set);
        tvFavorite = (TextView) findViewById(R.id.tv_drawer_favorite);

        ivHead.setOnClickListener(this);
        tvSet.setOnClickListener(this);
        tvFavorite.setOnClickListener(this);

//        mPresenter.getUserInfo();

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }


    /**
     * 弹出日起选择
     */
    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetDialog);
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_main_calendar, null);
        final MaterialCalendarView widget = (MaterialCalendarView) v.findViewById(R.id.cv_main_calendar);
        widget.setSelectedDate(CalendarDay.today());
        widget.state().edit()
                .setMaximumDate(CalendarDay.today()).commit();
        widget.state().edit()
                .setMinimumDate(CalendarDay.from(2013, 4, 20)).commit();

        Button btnCancel = (Button) v.findViewById(R.id.btn_main_calendar_cancel);
        btnCancel.setOnClickListener(view -> {
            if (mBottomSheetDialog != null) {
                mBottomSheetDialog.dismiss();
            }
        });
        Button btnSure = (Button) v.findViewById(R.id.btn_main_calendar_sure);
        btnSure.setOnClickListener(view -> {
            mPresenter.getNewsByDate(widget.getSelectedDate());
            if (mBottomSheetDialog != null) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.contentView(v)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_drawer_head:
                if (!BaseApplication.isLogin) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_drawer_set:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.tv_drawer_favorite:
                if (BaseApplication.isLogin) {
                    startActivity(new Intent(MainActivity.this, FavoriteListActivity.class));
                } else {
                    showToast("请先登录");
                }
                break;
        }
        drawerLayout.closeDrawers();
    }

    @Override
    public void setNews(NewsBean newsBean) {
        if (newsBean.getStories() != null) {
            listStories = newsBean.getStories();
            if (newsBean.getTop_stories() != null) {
                listTop = newsBean.getTop_stories();
            }
            adapter = new StoriesRecyclerAdapter(MainActivity.this, listStories, listTop, newsBean.getDate());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (refreshLayout != null) {
            refreshLayout.onFinishFreshAndLoad();
        }
    }

    @Override
    public void setDrawer(String name) {
        tvName.setText(name);
    }

    private boolean isBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isBack) {
                showToast("再次点击退出");
                isBack = true;
                Observable.timer(2, TimeUnit.SECONDS)
                        .subscribe(aLong -> {
                            isBack = false;
                        });
                return true;
            } else {
                MainActivity.this.finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }
}
