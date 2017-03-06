package cn.yumutech.news.api;

import cn.yumutech.news.beans.daily.DailyTimeLine;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by 小豪 on 2017/3/6.
 */

public interface DailyApi {
    @GET("homes/index/{num}.json")
    Observable<DailyTimeLine> getDailyTimeLine(@Path("num") String num);

    @GET("options/index/{id}/{num}.json")
    Observable<DailyTimeLine> getDailyFeedDetail(@Path("id") String id,@Path("num") String num);
}
