package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cn.yumutech.news.R;
import cn.yumutech.news.api.ApiFactory;
import cn.yumutech.news.api.ZhihuApi;
import cn.yumutech.news.beans.zhihu.NewsTimeLine;
import cn.yumutech.news.ui.adapter.ZhihuListAdapter;
import cn.yumutech.news.ui.view.IZhihuFgView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 小豪 on 2017/3/2.
 */

public class zhihuFgPresenterImpl implements zhihuPresenter {

    private IZhihuFgView mIZhihuFgView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private ZhihuListAdapter adapter;
    public static final ZhihuApi zhihuApi = ApiFactory.getZhihuApiSingleton();
    private int lastVisibleItem;
    private NewsTimeLine timeLine;
    private RecyclerView mRecyclerView;

    private boolean isLoadMore = false; // 是否加载过更多


    public zhihuFgPresenterImpl(Context context, IZhihuFgView iZhihuFgView) {
        this.context = context;
        this.mIZhihuFgView = iZhihuFgView;
    }


    @Override
    public void getLastNews() {
        layoutManager = mIZhihuFgView.getLayoutManager();
        mRecyclerView = mIZhihuFgView.getRecyclerView();
        if (mIZhihuFgView != null) {
            zhihuApi.getLatestNews()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newsTimeLine -> {
                        disPlayZhihuList(newsTimeLine, context, mIZhihuFgView, mRecyclerView);
                    },this::loadError);
        }
    }

    @Override
    public void getBeforeNews(String time) {
        zhihuApi.getBeforetNews(time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsTimeLine -> {
                    disPlayZhihuList(timeLine,context,mIZhihuFgView,mRecyclerView);
                },this::loadError);
    }

    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }


    String time;
    private void disPlayZhihuList(NewsTimeLine newsTimeLine, Context context, IZhihuFgView iZhihuFgView, RecyclerView recyclerView) {
        if (isLoadMore) {
            if (time == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                iZhihuFgView.setDataRefresh(false);
                return;
            }
            else {
                timeLine.getStories().addAll(newsTimeLine.getStories());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = newsTimeLine;
            adapter = new ZhihuListAdapter(context, timeLine);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        iZhihuFgView.setDataRefresh(false);
        time = newsTimeLine.getDate();
    }


    @Override
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                            .findLastVisibleItemPosition();
                    if (layoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(adapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()) {
                           adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        adapter.updateLoadStatus(adapter.LOAD_MORE);
                        new Handler().postDelayed(() -> getBeforeNews(time), 1000);
                    }
                }
            }


        });
    }


}
