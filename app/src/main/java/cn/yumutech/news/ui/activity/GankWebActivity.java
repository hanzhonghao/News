package cn.yumutech.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.yumutech.news.R;

public class GankWebActivity extends AppCompatActivity {


    public static final String GANK_URL = "gank_url";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_web);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, GankWebActivity.class);
        intent.putExtra(GankWebActivity.GANK_URL, url);
        return intent;
    }
}
