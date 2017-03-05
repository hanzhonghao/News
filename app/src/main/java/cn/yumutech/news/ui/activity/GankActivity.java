package cn.yumutech.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.yumutech.news.R;

public class GankActivity extends AppCompatActivity {
    private static final String DATE = "date";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
    }

    public static Intent newIntent(Context context, long date){
        Intent intent = new Intent(context,GankActivity.class);
        intent.putExtra(GankActivity.DATE,date);
        return intent;
    }
}
