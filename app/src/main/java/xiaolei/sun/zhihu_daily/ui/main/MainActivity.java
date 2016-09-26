package xiaolei.sun.zhihu_daily.ui.main;

import android.content.Intent;
import android.os.Bundle;
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
import com.orhanobut.logger.Logger;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;

import java.util.List;

import rx.Subscriber;
import xiaolei.sun.zhihu_daily.customerview.blurdrawer.BlurActionBarDrawerToggle;
import xiaolei.sun.zhihu_daily.customerview.blurdrawer.BlurDrawerLayout;
import xiaolei.sun.zhihu_daily.customerview.rainrefresh.BeautifulRefreshLayout;
import xiaolei.sun.zhihu_daily.network.api.ApiNewsDate;
import xiaolei.sun.zhihu_daily.network.entity.StoriesBean;
import xiaolei.sun.zhihu_daily.network.entity.TopStoriesBean;
import xiaolei.sun.zhihu_daily.ui.BaseActivity;
import xiaolei.sun.zhihu_daily.R;
import xiaolei.sun.zhihu_daily.network.entity.NewsBean;
import xiaolei.sun.zhihu_daily.ui.login.LoginActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private BlurDrawerLayout drawerLayout;
    private BlurActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StoriesRecyclerAdapter adapter;
    private FloatingActionButton btnCalendar;
    private BeautifulRefreshLayout refreshLayout;

    private SimpleDraweeView ivHead;
    private TextView tvName;

    private List<StoriesBean> listStories;
    private List<TopStoriesBean> listTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        NewsBean bean = (NewsBean) getIntent().getSerializableExtra("NEWS");
        listStories = bean.getStories();
        listTop = bean.getTop_stories();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new StoriesItemDecoration());

        adapter = new StoriesRecyclerAdapter(MainActivity.this, listStories, listTop, bean.getDate());
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (BlurDrawerLayout) findViewById(R.id.dl_activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        toggle = new BlurActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar,
                R.string.app_about, R.string.app_about);
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
//                Observable.timer(1, TimeUnit.MILLISECONDS)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(new Action1<Long>() {
//                            @Override
//                            public void call(Long aLong) {
//                                refreshLayout.finishRefreshing();
//                            }
//                        });
                getNewsByDate(CalendarDay.today());
            }
        });

        ivHead = (SimpleDraweeView) findViewById(R.id.ic_drawer_head);
        tvName = (TextView) findViewById(R.id.tv_drawer_name);
        ivHead.setOnClickListener(this);

    }

    private BottomSheetDialog mBottomSheetDialog;

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
                getNewsByDate(widget.getSelectedDate());
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                }
            }
        });
        mBottomSheetDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
        mBottomSheetDialog.contentView(v)
                .show();
    }

    /**
     * 根据日期获取当日文章
     *
     * @param date
     */
    private void getNewsByDate(CalendarDay date) {
        //20131119
        int month = date.getMonth() + 1;
        String strMonth = month + "";
        if (month < 10) {
            strMonth = "0" + month;
        }
        int day = date.getDay() + 1;
        String dateFomat = date.getYear() + strMonth + day;
        Logger.d("SelectedDate>>" + dateFomat);
        ApiNewsDate api = new ApiNewsDate();
        api.getNewsLasted(dateFomat, new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(NewsBean newsBean) {

                if (newsBean.getStories() != null) {
                    listStories = newsBean.getStories();
//                listTop = newsBean.getTop_stories();
                    adapter = new StoriesRecyclerAdapter(MainActivity.this, listStories, listTop, newsBean.getDate());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                if (refreshLayout != null) {
                    refreshLayout.finishRefreshing();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_drawer_head:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
    }
}
