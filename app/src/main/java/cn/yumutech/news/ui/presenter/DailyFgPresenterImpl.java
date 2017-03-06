package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import cn.yumutech.news.R;
import cn.yumutech.news.api.ApiFactory;
import cn.yumutech.news.api.DailyApi;
import cn.yumutech.news.beans.daily.DailyTimeLine;
import cn.yumutech.news.ui.adapter.DailyListAdapter;
import cn.yumutech.news.ui.view.IDailyFgView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 小豪 on 2017/3/6.
 */

public class DailyFgPresenterImpl implements DailyFgPresenter {

    private Context mContext;
    private IDailyFgView mIDailyFgView;
    public static final DailyApi dailyApi = ApiFactory.getDailyApiSingleton();
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String next_pager;
    private String has_more;
    private boolean isLoadMore = false; // 是否加载过更多
    private DailyTimeLine timeLine;
    private DailyListAdapter adapter;
    private int lastVisibleItem;

    public DailyFgPresenterImpl(Context context, IDailyFgView mIDailyFgView) {
        this.mContext = context;
        this.mIDailyFgView = mIDailyFgView;
    }

    @Override
    public void getDailyTimeLine(String num) {
        mLayoutManager = mIDailyFgView.getLayoutManager();
        mRecyclerView = mIDailyFgView.getRecyclerView();

        dailyApi.getDailyTimeLine(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyTimeLine -> {
                    if(dailyTimeLine.getMeta().getMsg().equals("success")){
                        disPlayDailyTimeLine(mContext,dailyTimeLine,mRecyclerView,mIDailyFgView);
                    }
                },this::loadError);
//        dailyApi.getDailyTimeLine(num)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<DailyTimeLine>() {
//                    @Override
//                    public void call(DailyTimeLine dailyTimeLine) {
//                        if (dailyTimeLine.getMeta().equals("success")) {
//                            disPlayDailyTimeLine(mContext, dailyTimeLine, mRecyclerView, mIDailyFgView);
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        loadError(throwable);
//                    }
//                });

    }

    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        mIDailyFgView.setDataRefresh(false);
        Toast.makeText(mContext, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void disPlayDailyTimeLine(Context context, DailyTimeLine dailyTimeLine, RecyclerView recyclerView, IDailyFgView dailyFgView) {
        if (dailyTimeLine.getResponse().getLast_key() != null) {
            next_pager = dailyTimeLine.getResponse().getLast_key();
        }
        has_more = dailyTimeLine.getResponse().getHas_more();
        if (isLoadMore) {
            if (dailyTimeLine.getResponse().getFeeds() == null) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                dailyFgView.setDataRefresh(false);
                return;
            } else {
                timeLine.getResponse().getFeeds().addAll(dailyTimeLine.getResponse().getFeeds());
            }
            adapter.notifyDataSetChanged();
        } else {
            timeLine = dailyTimeLine;
            adapter = new DailyListAdapter(context, timeLine.getResponse());
            recyclerView.setAdapter(adapter);
        }
        dailyFgView.setDataRefresh(false);
    }

    @Override
    public void scrollRecycleView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    if (mLayoutManager.getItemCount() == 1) {
                        adapter.updateLoadStatus(adapter.LOAD_NONE);
                        return;
                    }
                    if (lastVisibleItem + 1 == mLayoutManager.getItemCount()) {
                        adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                        if (has_more.equals("true")) {
                            isLoadMore = true;
                        }
                        adapter.updateLoadStatus(adapter.LOAD_MORE);
                        new Handler().postDelayed(() -> getDailyTimeLine(next_pager), 1000);
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
}
