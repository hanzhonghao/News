package cn.yumutech.news.beans.daily;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小豪 on 2017/3/6.
 */

public class Response implements Serializable{
    private List<Daily> banners;
    private List<Daily> feeds;
    private Daily headline;
    private List<Options> options;
    private String has_more;
    private String last_key;

    public List<Options> getOptions() {
        return options;
    }

    public List<Daily> getBanners() {
        return banners;
    }

    public List<Daily> getFeeds() {
        return feeds;
    }

    public Daily getHeadline() {
        return headline;
    }

    public String getHas_more() {
        return has_more;
    }

    public String getLast_key() {
        return last_key;
    }

    public int getListSize(){
        int size = 0;
        if(banners!=null&&feeds!=null){
            size = feeds.size()+1;
        }
        if(headline.getPost()!=null){
            size = size +1;
        }
        return size;
    }

    @Override
    public String toString() {
        return "Response{" +
                "banners=" + banners +
                ", feeds=" + feeds +
                ", headline=" + headline +
                ", options=" + options +
                ", has_more='" + has_more + '\'' +
                ", last_key='" + last_key + '\'' +
                '}';
    }
}
