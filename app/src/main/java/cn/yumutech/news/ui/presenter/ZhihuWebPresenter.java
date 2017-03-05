package cn.yumutech.news.ui.presenter;

/**
 * Created by 小豪 on 2017/3/3.
 */

public interface ZhihuWebPresenter {
    void getDetailNews(String id);
    void loadError(Throwable throwable);

}
