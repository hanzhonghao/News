package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import cn.yumutech.news.R;
import cn.yumutech.news.api.ApiFactory;
import cn.yumutech.news.api.GankApi;
import cn.yumutech.news.beans.gank.Gank;
import cn.yumutech.news.beans.gank.Meizhi;
import cn.yumutech.news.beans.gank.Video;
import cn.yumutech.news.ui.adapter.GankListAdapter;
import cn.yumutech.news.ui.view.IGankFgView;
import cn.yumutech.news.util.DateUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 小豪 on 2017/3/4.
 */

public class GankFgPresenterImpl implements GankFgPresenter {

    public static final GankApi gankApi = ApiFactory.getGankApiSingleton();
    private Context mContext;
    private IGankFgView mIGankView;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private int page = 1;
    private List<Gank> list;
    private int lastVisibleItem;
    private GankListAdapter adapter;
    private boolean isLoadMore = false; // 是否加载过更多


    public GankFgPresenterImpl(Context context, IGankFgView iGankView) {
        this.mContext = context;
        this.mIGankView = iGankView;
    }

    @Override
    public void getGankData() {
        mRecyclerView = mIGankView.getRecyclerView();
        mLayoutManager = mIGankView.getLayoutManager();

        if (isLoadMore) {
            page = page + 1;
        }

        Observable.zip(gankApi.getMeizhiData(page), gankApi.getVideoData(page), this::creatDesc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meizhi1 -> {
                    displayMeizhi(mContext, meizhi1.getResults(), mIGankView, mRecyclerView);
                },this::loadError);
//        subscribe(new Action1<Meizhi>() {
//                    @Override
//                    public void call(Meizhi meizhi) {
//                        displayMeizhi(mContext, meizhi.getResults(), mIGankView, mRecyclerView);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        loadError(throwable);
//                    }
//                });

    }

    private Meizhi creatDesc(Meizhi meizhi, Video video) {
        for (Gank gankmeizhi : meizhi.getResults()) {
            gankmeizhi.desc = gankmeizhi.desc + " " +
                    getVideoDesc(gankmeizhi.getPublishedAt(), video.getResults());
        }
        return meizhi;
    }

    //将妹子图片对应的描述匹配到对应视频的描述。
    private String getVideoDesc(Date publishedAt, List<Gank> results) {
        String videoDesc = "";
        for (int i = 0; i < results.size(); i++) {
            Gank video = results.get(i);
            if (video.getPublishedAt() == null) video.setPublishedAt(video.getCreatedAt());
            if (DateUtils.isSameData(publishedAt, video.getPublishedAt())) {
                videoDesc = video.getDesc();
                break;
            }
        }
        return videoDesc;

    }

    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.gank_load_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager
                            .findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager
                            .getItemCount()) {
                        mIGankView.setDataRefresh(true);
                        isLoadMore = true;
                        new Handler().postDelayed(() -> getGankData(), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }


    @Override
    public void displayMeizhi(Context context, List<Gank> meizhiList, IGankFgView gankFgView, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (meizhiList == null) {
                mIGankView.setDataRefresh(false);
                return;
            } else {
                list.addAll(meizhiList);
            }
            adapter.notifyDataSetChanged();
        }else{
            list = meizhiList;
            adapter = new GankListAdapter(context,list);
            mRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        mIGankView.setDataRefresh(false);
    }

}
