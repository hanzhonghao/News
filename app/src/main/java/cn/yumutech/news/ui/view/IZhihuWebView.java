package cn.yumutech.news.ui.view;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 小豪 on 2017/3/3.
 */

public interface IZhihuWebView {
    WebView getWebView();
    ImageView getWebImg();
    TextView getImgTitle();
    TextView getImgSource();
}
