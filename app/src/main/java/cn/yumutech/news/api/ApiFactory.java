package cn.yumutech.news.api;


public class ApiFactory {

    protected static final Object monitor = new Object();
    static ZhihuApi zhihuApiSingleton = null;
    static GankApi gankApiSingleton = null;
    static DailyApi dailyApiSingleton = null;

    //return Singleton
    public static ZhihuApi getZhihuApiSingleton() {
        synchronized (monitor) {
            if (zhihuApiSingleton == null) {
                zhihuApiSingleton = new ApiRetrofit().getZhihuApiService();
            }
            return zhihuApiSingleton;
        }
    }

    public static GankApi getGankApiSingleton() {
        synchronized (monitor) {
            if (gankApiSingleton == null) {
                gankApiSingleton = new ApiRetrofit().getGankApiService();
            }
            return gankApiSingleton;
        }
    }

    public static DailyApi getDailyApiSingleton() {
        synchronized (monitor) {
            if (dailyApiSingleton == null) {
                dailyApiSingleton = new ApiRetrofit().getDailyApiService();
            }
            return dailyApiSingleton;
        }
    }

}
