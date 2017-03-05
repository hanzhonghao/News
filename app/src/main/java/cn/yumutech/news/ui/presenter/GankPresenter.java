package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.yumutech.news.beans.gank.Gank;
import cn.yumutech.news.ui.view.IGankView;

/**
 * Created by 小豪 on 2017/3/5.
 */

public interface GankPresenter {
    void getGankList(int year,int month,int day);
    void loadError(Throwable throwable);
    void displayMeizhi(Context context, List<Gank> gankList, IGankView gankView, RecyclerView recyclerView);
}
