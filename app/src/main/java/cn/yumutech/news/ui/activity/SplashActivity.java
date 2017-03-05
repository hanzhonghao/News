package cn.yumutech.news.ui.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.BuildConfig;
import cn.yumutech.news.R;
import cn.yumutech.news.widget.SplashView;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private Handler mHandler = new Handler();

    @Bind(R.id.splash_view)
    SplashView splash_view;
    @Bind(R.id.tv_splash_info)
    TextView tv_splash_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AssetManager assets = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(assets, "fonts/rm_albion.ttf");//根据路径得到Typeface
        tv_splash_info.setTypeface(tf);//设置字体
        startLoadingData();
    }

    /**
     * start splash animation
     */
    private void startLoadingData() {
        Random random = new Random();//给loading data随机1~3秒
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1000 + random.nextInt(2000));
    }

    private void onLoadingDataEnded() {
        //开启splash动画
        splash_view.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
                // log the animation start event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash started");
                }
            }

            @Override
            public void onUpdate(float completionFraction) {
                // log animation update events
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash at " + String.format("%.2f", (completionFraction * 100)) + "%");
                }
            }

            @Override
            public void onEnd() {
                // log the animation end event
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "splash ended");
                }
                // free the view so that it turns into garbage
                splash_view = null;
                goToMain();
            }
        });
    }

    private void goToMain() {
        finish();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
