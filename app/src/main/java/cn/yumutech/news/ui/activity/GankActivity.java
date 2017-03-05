package cn.yumutech.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import cn.yumutech.news.ui.presenter.GankPresenterImpl;
import cn.yumutech.news.ui.view.IGankView;

public class GankActivity extends AppCompatActivity implements IGankView {
    private static final String DATE = "date";
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.gank_list)
    RecyclerView mGankList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsRequestDataRefresh = false;
    private GankPresenterImpl mGankPresenter;

    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGankList.setLayoutManager(mLinearLayoutManager);
        mGankPresenter = new GankPresenterImpl(this, this);

        if (isSetRefresh()){
            setupSwipeRefresh();
        }
        mToolbar.setTitle("Gank の 今日特供呦");
//        setTitle("Gank の 今日特供");
        parseIntent();
        setDataRefresh(true);
        initToolBar();
        mGankPresenter.getGankList(year, month, day);
    }


    private void initToolBar() {
        if (mToolbar != null && mAppBarLayout != null) {
            setSupportActionBar(mToolbar);
            if (canBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    mAppBarLayout.setElevation(10.6f);//Z轴浮动
                }
            }
        }
    }

    private void setupSwipeRefresh() {
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2, R.color.refresh_progress_3);
            mSwipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    public void requestDataRefresh() {
        setDataRefresh(true);
        mGankPresenter.getGankList(year, month, day);
    }

    /**
     * 判断Activity是否需要刷新功能
     *
     */
    public Boolean isSetRefresh() {
        return true;
    }

    /**
     * 判断当前 Activity 是否允许返回
     * 主界面不允许返回，次级界面允许返回
     *
     * @return false
     */

    public boolean canBack() {
        return true;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mGankList;
    }

    @Override
    public void setDataRefresh(boolean refresh) {
        setRefresh(refresh);
    }

    private void setRefresh(boolean requestDataRefresh) {
        if (mSwipeRefresh == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            mSwipeRefresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefresh != null) {
                        mSwipeRefresh.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            mSwipeRefresh.setRefreshing(true);
        }

    }

    private void parseIntent() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getIntent().getLongExtra(DATE, 0));
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static Intent newIntent(Context context, long date) {
        Intent intent = new Intent(context, GankActivity.class);
        intent.putExtra(GankActivity.DATE, date);
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //此时android.R.id.home即为返回箭头
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);

        }
    }
}
