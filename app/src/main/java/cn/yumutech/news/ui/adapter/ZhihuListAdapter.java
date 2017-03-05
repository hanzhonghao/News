package cn.yumutech.news.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.yumutech.news.MyApp;
import cn.yumutech.news.R;
import cn.yumutech.news.beans.zhihu.NewsTimeLine;
import cn.yumutech.news.beans.zhihu.Stories;
import cn.yumutech.news.beans.zhihu.TopStories;
import cn.yumutech.news.ui.activity.ZhihuWebActivity;
import cn.yumutech.news.util.ScreenUtil;
import cn.yumutech.news.widget.TopStoriesAutoBanner;

/**
 * Created by Werb on 2016/8/18.
 * Werb is Wanbo.
 * Contact Me : werbhelius@gmail.com
 * Zhihu List
 */
public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private NewsTimeLine newsTimeLine;
    private int status = 1;
    public static final int LOAD_MORE = 0;
    public static final int LOAD_PULL_TO = 1;
    public static final int LOAD_NONE = 2;
    public static final int LOAD_END = 3;
    private static final int TYPE_TOP = -1;
    private static final int TYPE_FOOTER = -2;

    public ZhihuListAdapter(Context context, NewsTimeLine newsTimeLine) {
        this.context = context;
        this.newsTimeLine = newsTimeLine;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View rootView = View.inflate(parent.getContext(), R.layout.item_zhihu_top_stories, null);
            return new TopStoriesViewHolder(rootView, newsTimeLine.getTop_stories());
        } else if (viewType == TYPE_FOOTER) {
            View view = View.inflate(parent.getContext(), R.layout.activity_view_footer, null);
            return new FooterViewHolder(view);
        } else {
            View rootView = View.inflate(parent.getContext(), R.layout.item_zhihu_stories, null);
            return new StoriesViewHolder(rootView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.bindItem();
        } else if (holder instanceof TopStoriesViewHolder) {
//            TopStoriesViewHolder topStoriesViewHolder = (TopStoriesViewHolder) holder;
//            topStoriesViewHolder.bindItem(newsTimeLine.getTop_stories());
        } else if (holder instanceof StoriesViewHolder) {
            StoriesViewHolder storiesViewHolder = (StoriesViewHolder) holder;
            storiesViewHolder.bindItem(newsTimeLine.getStories().get(position - 1));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (newsTimeLine.getTop_stories() != null) {
            if (position == 0) {
                return TYPE_TOP;
            } else if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return position;
            }
        } else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return position;
        }
    }

    @Override
    public int getItemCount() {
        return newsTimeLine.getStories().size() + 2;
    }


    /**
     * footer view
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_load_prompt)
        TextView tv_load_prompt;
        @Bind(R.id.progress)
        ProgressBar progress;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.instance(context).dip2px(40));
            itemView.setLayoutParams(params);
        }

        private void bindItem() {
            switch (status) {
                case LOAD_MORE:
                    progress.setVisibility(View.VISIBLE);
                    tv_load_prompt.setText("正在加载...");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_PULL_TO:
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("上拉加载更多");
                    itemView.setVisibility(View.VISIBLE);
                    break;
                case LOAD_NONE:
                    System.out.println("LOAD_NONE----");
                    progress.setVisibility(View.GONE);
                    tv_load_prompt.setText("已无更多加载");
                    break;
                case LOAD_END:
                    itemView.setVisibility(View.GONE);
                default:
                    break;
            }
        }
    }


    /**
     * TopStories
     */
    class TopStoriesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.vp_top_stories)
        TopStoriesAutoBanner vp_top_stories;
        @Bind(R.id.tv_top_title)
        TextView tv_top_title;
        private List<TopStories> mTopList;


        public TopStoriesViewHolder(View itemView, List<TopStories> topList) {
            super(itemView);
            this.mTopList = topList;
            ButterKnife.bind(this, itemView);
            initView();
            initData(mTopList);
        }

        private void initView() {
            vp_top_stories.setDotGravity(TopStoriesAutoBanner.DotGravity.RIGHT);
            vp_top_stories.setWaitMilliSceond(3000);
            vp_top_stories.setDotMargin(4);
        }

        private void initData(List<TopStories> toplist) {
//            System.out.println("toplist:~~~~~~~~~~~~~~"+toplist);
            MyAutoBannerAdapter autoBannerAdapter = new MyAutoBannerAdapter(context);
            autoBannerAdapter.changeItems(toplist);
            vp_top_stories.setAdapter(autoBannerAdapter);
        }


        private class MyAutoBannerAdapter implements TopStoriesAutoBanner.AutoBannerAdapter {
            List<TopStories> lists;
            Context context;

            public MyAutoBannerAdapter(Context context) {
                this.context = context;
                this.lists = new ArrayList<>();
            }

            @Override
            public int getCount() {
                return lists.size();
            }

            public void changeItems(@NonNull List<TopStories> lists) {
                this.lists.clear();
                this.lists.addAll(lists);
                System.out.println("toplist:~~~~~~~~~~~~~~"+lists);
            }

            @Override
            public View getView(View convertView, int position) {
                ImageView imageView;
                TopStories topstories;
                TopStories topstories1;
                if (convertView == null) {
                    imageView = new ImageView(context);
                } else {
                    imageView = (ImageView) convertView;
                }

//                System.out.println("toplist:~~~~~~~~~~~~~~"+lists.get(position));

                int textPosition=position-1;
                if(textPosition<0){
                    textPosition=lists.size()-1;
                }

                topstories = lists.get(position);
                topstories1 =lists.get(textPosition);
                System.out.println("position"+position);
                System.out.println("textPosition"+textPosition);
                Glide.with(MyApp.mContext).load(topstories.getImage()).centerCrop()
                        .into(imageView);

                tv_top_title.setText(topstories1.getTitle());

//                System.out.println("topstories.getTitle()"+ topstories.getTitle());
//                System.out.println("topstories.getImage()"+ topstories1.getImage());

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ZhihuWebActivity.class);
                        intent.putExtra("id",topstories.getId());
                        context.startActivity(intent);
                    }
                });
                return imageView;
            }
        }
    }

    /**
     * Stories
     */
    class StoriesViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.card_stories)
        CardView card_stories;
        @Bind(R.id.tv_stories_title)
        TextView tv_stories_title;
        @Bind(R.id.iv_stories_img)
        ImageView iv_stories_img;

        public StoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            ScreenUtil screenUtil = ScreenUtil.instance(context);
            int screenWidth = screenUtil.getScreenWidth();
            card_stories.setLayoutParams(new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

        }

        public void bindItem(Stories stories) {
            tv_stories_title.setText(stories.getTitle());
            String[] images = stories.getImages();
            Glide.with(context).load(images[0]).centerCrop().into(iv_stories_img);
            card_stories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ZhihuWebActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("id",stories.getId());
                    context.startActivity(intent);
                }
            });
//            card_stories.setOnClickListener(v -> context.startActivity(ZhihuWebActivity.newIntent(context, stories.getId())));
        }
    }

    // change recycler state
    public void updateLoadStatus(int status) {
        this.status = status;
        notifyDataSetChanged();
    }
}
