package cn.yumutech.news.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 小豪 on 2017/3/2.
 */

public class ViewPagerFgAdapter extends FragmentPagerAdapter {
    private String tag;
    private List<Fragment> mFragmentList;

    public ViewPagerFgAdapter(FragmentManager fm, List<Fragment> fragmentList, String tag) {
        super(fm);
        this.mFragmentList = fragmentList;
        this.tag = tag;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);

    }

    @Override
    public int getCount() {
        if (mFragmentList != null) {
            return mFragmentList.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tag.equals("main_view_pager")) {
            switch (position) {
                case 0:
                    return "知乎";
                case 1:
                    return "干货";
                case 2:
                    return "News World";
            }
        }
        return null;
    }
}
