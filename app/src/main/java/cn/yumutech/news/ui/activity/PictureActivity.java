package cn.yumutech.news.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import uk.co.senab.photoview.PhotoViewAttacher;

import static cn.yumutech.news.R.id.iv_meizhi_pic;

public class PictureActivity extends AppCompatActivity {

    public static final String IMG_URL = "img_url";
    public static final String IMG_DESC = "img_desc";
    public static final String TRANSIT_PIC = "picture";
//    @Bind(R.id.container)
//    CoordinatorLayout mContainer;
    private String img_url;
    private String img_desc;

    @Bind(iv_meizhi_pic)
    ImageView mIvMeizhiPic;
    @Bind(R.id.save_img)
    FloatingActionButton mSaveImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        initView();
        parseIntent();
        //设置共享元素
        ViewCompat.setTransitionName(mIvMeizhiPic, TRANSIT_PIC);
        Glide.with(this).load(img_url).centerCrop().into(mIvMeizhiPic);
        new PhotoViewAttacher(mIvMeizhiPic);
    }

    private void parseIntent() {
        img_url = getIntent().getStringExtra(IMG_URL);
        img_desc = getIntent().getStringExtra(IMG_DESC);
    }

    private void initView() {
        mSaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.IMG_URL, url);
        intent.putExtra(PictureActivity.IMG_DESC, desc);
        return intent;
    }

    private void saveImage() {
        mIvMeizhiPic.buildDrawingCache();
        Bitmap bitmap = mIvMeizhiPic.getDrawingCache();
        //将Bitmap转换成二进制，写入本地
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhigan");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, img_desc.substring(0, 10) + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(byteArray, 0, byteArray.length);
            fos.flush();

            //用广播通知相册进行更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            PictureActivity.this.sendBroadcast(intent);
//            Snackbar.make(mContainer, "图片保存成功~恭喜你收获到新的妹子~~", Snackbar.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.clear(mIvMeizhiPic);
    }


}
