package cn.yumutech.news.ui.presenter;


/**
 * Created by 小豪 on 2017/3/2.
 */

public interface zhihuPresenter {
    void getLastNews();
    void getBeforeNews(String time);
    void loadError(Throwable throwable);
    void scrollRecycleView();
}
