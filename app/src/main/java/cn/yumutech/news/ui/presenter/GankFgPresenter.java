package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.yumutech.news.beans.gank.Gank;
import cn.yumutech.news.ui.view.IGankFgView;

/**
 * Created by 小豪 on 2017/3/4.
 */

public interface GankFgPresenter {
    void getGankData();

    void loadError(Throwable throwable);

    void displayMeizhi(Context context, List<Gank> meizhiList, IGankFgView gankFgView, RecyclerView recyclerView);

    void scrollRecycleView();
}
