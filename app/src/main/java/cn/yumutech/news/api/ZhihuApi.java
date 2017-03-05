package cn.yumutech.news.api;


import rx.Observable;

import cn.yumutech.news.beans.zhihu.News;
import cn.yumutech.news.beans.zhihu.NewsTimeLine;
import cn.yumutech.news.beans.zhihu.SplashImage;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Werb on 2016/8/18.
 * Werb is Wanbo.
 * Contact Me : werbhelius@gmail.com
 * get Zhihu with retrofit
 */
public interface ZhihuApi {

    @GET("start-image/1080*1920")
    Observable<SplashImage> getSplashImage();

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();

    @GET("news/before/{time}")
    Observable<NewsTimeLine> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<News> getDetailNews(@Path("id") String id);
}
