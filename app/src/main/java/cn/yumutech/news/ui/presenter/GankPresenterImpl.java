package cn.yumutech.news.ui.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import cn.yumutech.news.R;
import cn.yumutech.news.api.ApiFactory;
import cn.yumutech.news.api.GankApi;
import cn.yumutech.news.beans.gank.Gank;
import cn.yumutech.news.beans.gank.GankData;
import cn.yumutech.news.ui.adapter.GankActivityAdapter;
import cn.yumutech.news.ui.view.IGankView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 小豪 on 2017/3/5.
 */

public class GankPresenterImpl implements GankPresenter {
    private Context mContext;
    private IGankView mIGankView;
    private RecyclerView mRecyclerView;
    public static final GankApi gankApi = ApiFactory.getGankApiSingleton();


    public GankPresenterImpl(Context context, IGankView mIGankView) {
        this.mContext = context;
        this.mIGankView = mIGankView;
    }

    @Override
    public void getGankList(int year, int month, int day) {
        mRecyclerView = mIGankView.getRecyclerView();
        gankApi.getGankData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GankData>() {
                    @Override
                    public void call(GankData gankData) {
                        displayMeizhi(mContext,gankData.results.getAllResults(),mIGankView,mRecyclerView);
                    }
                });

    }

    @Override
    public void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(mContext, R.string.gank_load_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayMeizhi(Context context, List<Gank> gankList, IGankView gankView, RecyclerView recyclerView) {
        GankActivityAdapter adapter = new GankActivityAdapter(context, gankList);
        recyclerView.setAdapter(adapter);
        gankView.setDataRefresh(false);
    }
}
