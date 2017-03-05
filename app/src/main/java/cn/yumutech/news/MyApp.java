package cn.yumutech.news;

import android.app.Application;
import android.content.Context;

/**
 * Created by 小豪 on 2017/3/2.
 */

public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
