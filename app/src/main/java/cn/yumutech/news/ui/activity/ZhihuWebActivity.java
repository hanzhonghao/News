package cn.yumutech.news.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.MyApp;
import cn.yumutech.news.R;
import cn.yumutech.news.ui.presenter.ZhihuWebPresenterImpl;
import cn.yumutech.news.ui.view.IZhihuWebView;

public class ZhihuWebActivity extends AppCompatActivity implements IZhihuWebView {


    @Bind(R.id.iv_web_img)
    ImageView mIvWebImg;
    @Bind(R.id.tv_img_title)
    TextView mTvImgTitle;
    @Bind(R.id.tv_img_source)
    TextView mTvImgSource;
    @Bind(R.id.web_view)
    WebView mWebView;
    private ZhihuWebPresenterImpl mZhihuWebPresenter;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_web);
        ButterKnife.bind(this);
        parseIntent();
        mZhihuWebPresenter = new ZhihuWebPresenterImpl(MyApp.mContext, this);
        mZhihuWebPresenter.getDetailNews(id);
    }

    private void parseIntent() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    public WebView getWebView() {
        return mWebView;
    }

    @Override
    public ImageView getWebImg() {
        return mIvWebImg;
    }

    @Override
    public TextView getImgTitle() {
        return mTvImgTitle;
    }

    @Override
    public TextView getImgSource() {
        return mTvImgSource;
    }
}
