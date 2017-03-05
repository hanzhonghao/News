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
import cn.yumutech.news.ui.presenter.zhihuFgPresenterImpl;
import cn.yumutech.news.ui.view.IZhihuFgView;

import static cn.yumutech.news.MyApp.mContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuFragment extends Fragment implements IZhihuFgView {
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.content_list)
    RecyclerView content_list;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private boolean mIsRequestDataRefresh = false;

    private zhihuFgPresenterImpl mZhihuFgPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mZhihuFgPresenter = new zhihuFgPresenterImpl(getContext(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_zhihu,container,false);
        ButterKnife.bind(this,rootView);
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
        mZhihuFgPresenter.getLastNews();
        mZhihuFgPresenter.scrollRecycleView();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        content_list.setLayoutManager(mLayoutManager);
    }

    public void setupSwipeRefresh(){
        if(mRefreshLayout != null){
            mRefreshLayout.setColorSchemeResources(R.color.refresh_progress_1,
                    R.color.refresh_progress_2,R.color.refresh_progress_3);
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,mContext.getResources().getDisplayMetrics()));
            mRefreshLayout.setOnRefreshListener(this::requestDataRefresh);
        }
    }

    public void requestDataRefresh() {
        setDataRefresh(true);
        mZhihuFgPresenter.getLastNews();
    }

    @Override
    public void setDataRefresh(Boolean refresh) {
       setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return content_list;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public Boolean isSetRefresh(){
        return true;
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (mRefreshLayout ==null){
            return;
        }
        if (!requestDataRefresh){
            mIsRequestDataRefresh = false;
            mRefreshLayout.postDelayed(()->{
                if (mRefreshLayout != null) {
                    mRefreshLayout.setRefreshing(false);
                }
            },1000);
        }else{
            mRefreshLayout.setRefreshing(true);
        }
    }
}
