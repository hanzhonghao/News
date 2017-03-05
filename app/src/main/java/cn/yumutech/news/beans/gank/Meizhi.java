package cn.yumutech.news.beans.gank;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 小豪 on 2017/3/4.
 */

public class Meizhi implements Serializable{
    private boolean error;
    private List<Gank> results;

    public List<Gank> getResults() {
        return results;
    }

    public boolean isError() {
        return error;
    }

    @Override
    public String toString() {
        return "Meizhi{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
