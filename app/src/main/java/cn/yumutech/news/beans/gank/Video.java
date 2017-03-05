package cn.yumutech.news.beans.gank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小豪 on 2017/3/4.
 */

public class Video implements Serializable{
    private boolean error;
    private List<Gank> results;

    public boolean isError() {
        return error;
    }

    public List<Gank> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "Video{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
