package cn.yumutech.news.ui.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by 小豪 on 2017/3/5.
 */
public interface IGankView {
    RecyclerView getRecyclerView();
    void setDataRefresh(boolean refresh);
}
