package cn.yumutech.news.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import cn.yumutech.news.ui.presenter.GankFgPresenterImpl;
import cn.yumutech.news.ui.view.IGankFgView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFgFragment extends Fragment implements IGankFgView {

    private GridLayoutManager mGridLayoutManager;
    @Bind(R.id.content_list)
    RecyclerView mContentList;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private boolean mIsRequestDataRefresh = false;

    private GankFgPresenterImpl mGankFgPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGankFgPresenter = new GankFgPresenterImpl(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, view);
        initView();
        if (isSetRefresh()) {
            setupSwipeRefresh();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setDataRefresh(true);
        mGankFgPresenter.getGankData();
        mGankFgPresenter.scrollRecycleView();
    }

    private void initView() {
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mContentList.setLayoutManager(mGridLayoutManager);
    }

    private void setupSwipeRefresh() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2,R.color.refresh_progress_3);
            mSwipeRefresh.setProgressViewOffset(true,0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
            mSwipeRefresh.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    public void requestDataRefresh(){
        setDataRefresh(true);
        mGankFgPresenter.getGankData();

    }


    public void setRefresh(boolean requestDataRefresh) {
        if (mSwipeRefresh==null){
            return;
        }
        if (!requestDataRefresh){
            mIsRequestDataRefresh = false;
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public  Boolean isSetRefresh() {
        return true;
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public GridLayoutManager getLayoutManager() {
        return mGridLayoutManager;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mContentList;
    }
}
