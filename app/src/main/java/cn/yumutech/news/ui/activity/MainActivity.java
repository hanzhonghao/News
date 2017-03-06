package cn.yumutech.news.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import cn.yumutech.news.ui.adapter.ViewPagerFgAdapter;
import cn.yumutech.news.ui.fragment.DailyFragment;
import cn.yumutech.news.ui.fragment.GankFgFragment;
import cn.yumutech.news.ui.fragment.ZhihuFragment;
import cn.yumutech.news.ui.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {


    private List<Fragment> fragmentList;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.content_viewPager)
    ViewPager content_viewPager;
    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @Bind(R.id.setting_bt)
    Button mSettingBt;
    @Bind(R.id.quit_bt)
    Button mQuitBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initTabView();
        if (mToolbar != null && mAppBarLayout != null) {
            setSupportActionBar(mToolbar);
            if (canBack()) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(true);//设置actionBar一个返回箭头，主界面没有，次级界面有
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    mAppBarLayout.setElevation(10.6f);//Z轴浮动
                }
            }
        }
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switchNavigation(item.getItemId());
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void switchNavigation(int id) {
        switch (id) {
            case R.id.navigation_item_news:
                content_viewPager.setCurrentItem(0);
                break;
            case R.id.navigation_item_images:
                content_viewPager.setCurrentItem(1);
                break;
            case R.id.navigation_item_weather:
                content_viewPager.setCurrentItem(2);
                break;
            case R.id.navigation_item_about:
                startActivity(new Intent(this, AboutMeActivity.class));
                break;
            default:
                content_viewPager.setCurrentItem(0);
        }
    }


    //初始化Tab
    private void initTabView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ZhihuFragment());
        fragmentList.add(new GankFgFragment());
        fragmentList.add(new DailyFragment());
        content_viewPager.setOffscreenPageLimit(3);//设置至少3个fragment，防止重复创建和销毁，造成内存溢出
        content_viewPager.setAdapter(new ViewPagerFgAdapter(getSupportFragmentManager(), fragmentList, "main_view_pager"));//给ViewPager设置适配器
        tabLayout.setupWithViewPager(content_viewPager);//将TabLayout和ViewPager关联起来
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.today_github) {
            String github_trending = "https://github.com/trending";
            Intent intent = new Intent(this, GankWebActivity.class);
            startActivity(intent);
//            startActivity(GankWebActivity.newIntent(this,github_trending));
            return true;
        } else if (item.getItemId() == R.id.about_me) {
            startActivity(new Intent(this, AboutMeActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * 判断当前 Activity 是否允许返回
     * 主界面不允许返回，次级界面允许返回
     *
     * @return false
     */
    public boolean canBack() {
        return false;
    }

    public void settingOnClick(View v){
        Intent intent = new Intent(this, GankWebActivity.class);
        startActivity(intent);
    }

    public void quitOnClick(View v){
        finish();
        System.exit(0);
    }
}
