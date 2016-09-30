package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

import java.util.List;

import xiaolei.sun.zhihu_daily.ZhihuDailyApplication;
import xiaolei.sun.zhihu_daily.customerview.blurdrawer.BlurActionBarDrawerToggle;
import xiaolei.sun.zhihu_daily.customerview.blurdrawer.BlurDrawerLayout;
import xiaolei.sun.zhihu_daily.customerview.rainrefresh.BeautifulRefreshLayout;
import xiaolei.sun.zhihu_daily.network.entity.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.TopStoriesBean;
import xiaolei.sun.zhihu_daily.ui.base.BaseActivity;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteActivity;
import xiaolei.sun.zhihu_daily.ui.favorite.FavoriteListActivity;
import xiaolei.sun.zhihu_daily.ui.login.LoginActivity;
import xiaolei.sun.zhihu_daily.ui.settings.SettingsActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainContract.View {

    private BlurDrawerLayout drawerLayout;
    private BlurActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StoriesRecyclerAdapter adapter;
    private FloatingActionButton btnCalendar;
    private BeautifulRefreshLayout refreshLayout;

    private BottomSheetDialog mBottomSheetDialog;

    private List<StoriesBean> listStories;
    private List<TopStoriesBean> listTop;

    private MainPresenter mPresenter;

    //Drawer
    private SimpleDraweeView ivHead;
    private TextView tvName;
    private TextView tvSet;
    private TextView tvFavorite;

    @Override
    public void init() {
        mPresenter = new MainPresenter(this);
        mPresenter.login();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (BlurDrawerLayout) findViewById(R.id.dl_activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        toggle = new BlurActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnCalendar = (FloatingActionButton) findViewById(R.id.btn_main_calendar);
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });

        refreshLayout = (BeautifulRefreshLayout) findViewById(R.id.refresh_main);
        refreshLayout.setBuautifulRefreshListener(new BeautifulRefreshLayout.BuautifulRefreshListener() {
            @Override
            public void onRefresh(BeautifulRefreshLayout refreshLayout) {
                mPresenter.getNewsByDate(CalendarDay.today());
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

        mPresenter.getUserInfo();

    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }


    /**
     * 弹出日起选择
     */
    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.Material_App_BottomSheetDialog);
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_main_calendar, null);
        final MaterialCalendarView widget = (MaterialCalendarView) v.findViewById(R.id.cv_main_calendar);
        widget.setSelectedDate(CalendarDay.today());
        widget.state().edit()
                .setMaximumDate(CalendarDay.today()).commit();
        widget.state().edit()
                .setMinimumDate(CalendarDay.from(2013, 4, 20)).commit();

        Button btnCancel = (Button) v.findViewById(R.id.btn_main_calendar_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
        Button btnSure = (Button) v.findViewById(R.id.btn_main_calendar_sure);
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getNewsByDate(widget.getSelectedDate());
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.contentView(v)
                .show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_drawer_head:
                if (!ZhihuDailyApplication.isLogin) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.tv_drawer_set:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.tv_drawer_favorite:
                startActivity(new Intent(MainActivity.this, FavoriteListActivity.class));
                break;
        }
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
            refreshLayout.finishRefreshing();
        }
    }

    @Override
    public void setDrawer(String name) {
        tvName.setText(name);
    }
}
