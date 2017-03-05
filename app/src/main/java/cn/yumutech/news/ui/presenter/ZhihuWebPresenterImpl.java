package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.yumutech.news.R;
import cn.yumutech.news.beans.zhihu.News;
import cn.yumutech.news.ui.view.IZhihuWebView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static cn.yumutech.news.ui.presenter.zhihuFgPresenterImpl.zhihuApi;

/**
 * Created by 小豪 on 2017/3/3.
 */

public class ZhihuWebPresenterImpl implements ZhihuWebPresenter {

    private Context context ;
    private IZhihuWebView mIZhiWebView;

    public ZhihuWebPresenterImpl(Context context, IZhihuWebView iZhiWebView) {
        this.context = context;
        this.mIZhiWebView = iZhiWebView;
    }
    @Override
    public void getDetailNews(String id) {
        zhihuApi.getDetailNews(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        setWebView(news);
                    }
                },this::loadError);

    }

    ImageView webImg;
    private void setWebView(News news) {
        WebView webView = mIZhiWebView.getWebView();
        webImg = mIZhiWebView.getWebImg();
        TextView imgSource = mIZhiWebView.getImgSource();
        TextView imgTitle = mIZhiWebView.getImgTitle();

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\""+news.getCss()[0]+"\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html =head + news.getBody().replace(img," ");
        webView.loadDataWithBaseURL(null,html,"text/html","utf-8",null);
        Glide.with(context).load(news.getImage()).centerCrop().into(webImg);

        imgTitle.setText(news.getTitle());
        imgSource.setText(news.getImage_source());
    }

    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
}
