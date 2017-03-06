package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import cn.yumutech.news.beans.daily.DailyTimeLine;
import cn.yumutech.news.ui.view.IDailyFgView;

/**
 * Created by 小豪 on 2017/3/6.
 */

public interface DailyFgPresenter {
    void getDailyTimeLine(String num);
    void loadError(Throwable throwable);
    void disPlayDailyTimeLine(Context context, DailyTimeLine dailyTimeLine, RecyclerView recyclerView, IDailyFgView dailyFgView);
    void scrollRecycleView();
}
