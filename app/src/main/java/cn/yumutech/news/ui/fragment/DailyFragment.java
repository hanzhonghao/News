package cn.yumutech.news.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import cn.yumutech.news.ui.presenter.DailyFgPresenterImpl;
import cn.yumutech.news.ui.view.IDailyFgView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyFragment extends Fragment implements IDailyFgView {

    @Bind(R.id.content_list)
    RecyclerView mContentList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mIsRequestDataRefresh = false;
    private DailyFgPresenterImpl mDailyFgPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDailyFgPresenter = new DailyFgPresenterImpl(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        if (isSetRefresh()){
            setupSwipeRefresh();
        }
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataRefresh(true);
        mDailyFgPresenter.getDailyTimeLine("0");
        mDailyFgPresenter.scrollRecycleView();
    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mContentList.setLayoutManager(mLinearLayoutManager);
    }

    private void setupSwipeRefresh() {
        if (mSwipeRefresh!=null){
            mSwipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2,R.color.refresh_progress_3);
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    private void requestDataRefresh() {
        setDataRefresh(true);
        mDailyFgPresenter.getDailyTimeLine("0");
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    private void setRefresh(Boolean requestDataRefresh) {
        if (mSwipeRefresh == null){
            return;
        }
        if (!requestDataRefresh){
            mIsRequestDataRefresh =false;
            mSwipeRefresh.postDelayed(()->{
                if (mSwipeRefresh!=null){
                    mSwipeRefresh.setRefreshing(false);
                }
            },1000);
        }else{
            mSwipeRefresh.setRefreshing(true);
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mContentList;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLinearLayoutManager;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public  boolean isSetRefresh() {
        return true;
    }

}
