package cn.yumutech.news.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by 小豪 on 2017/3/4.
 */
public interface IGankFgView {

    void setDataRefresh(Boolean refresh);
    GridLayoutManager getLayoutManager();
    RecyclerView getRecyclerView();
}
