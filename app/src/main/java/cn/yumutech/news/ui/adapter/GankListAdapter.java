package cn.yumutech.news.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.R;
import cn.yumutech.news.beans.gank.Gank;
import cn.yumutech.news.ui.activity.GankActivity;
import cn.yumutech.news.ui.activity.PictureActivity;

/**
 * Created by 小豪 on 2017/3/4.
 */

public class GankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Gank> mList;

    public GankListAdapter(Context context,List<Gank> list){
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_meizhi, parent, false);
        return new GankMeiZhiViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankMeiZhiViewHolder){
            GankMeiZhiViewHolder gankMeiZhiViewHolder = (GankMeiZhiViewHolder) holder;
            gankMeiZhiViewHolder.bindItem(mList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GankMeiZhiViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.card_meizhi)
        CardView card_meizhi;
        @Bind(R.id.iv_meizhi)
        ImageView iv_meizhi;
        @Bind(R.id.tv_meizhi_title)
        TextView tv_meizhi_title;

        public GankMeiZhiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindItem(Gank meizhi){
            tv_meizhi_title.setText(meizhi.getDesc());
            Glide.with(mContext).load(meizhi.getUrl()).centerCrop().into(iv_meizhi);

            //点击图片
            iv_meizhi.setOnClickListener(v -> {
//                Intent intent = new Intent(mContext, PictureActivity.class);
//                intent.putExtra("meizhiUrl",meizhi.getUrl());
//                intent.putExtra("meizhiDesc",meizhi.getDesc());
                Intent intent = PictureActivity.newIntent(mContext, meizhi.getUrl(), meizhi.getDesc());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) mContext, iv_meizhi, PictureActivity.TRANSIT_PIC);
                try{
                    ActivityCompat.startActivity((Activity) mContext,intent,optionsCompat.toBundle());
                }catch (Exception e){
                    e.printStackTrace();
                    mContext.startActivity(intent);
                }
            });

            //点击card
            card_meizhi.setOnClickListener(v -> {
                Intent intent = GankActivity.newIntent(mContext, meizhi.getPublishedAt().getTime());
                mContext.startActivity(intent);
            });
        }
    }
}
