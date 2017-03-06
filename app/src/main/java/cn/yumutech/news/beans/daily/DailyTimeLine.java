package cn.yumutech.news.beans.daily;

import java.io.Serializable;

/**
 * Created by 小豪 on 2017/3/6.
 */

public class DailyTimeLine implements Serializable {
    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "DailyTimeLine{" +
                "meta=" + meta +
                ", response=" + response +
                '}';
    }
}
