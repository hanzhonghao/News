package cn.yumutech.news.util;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by 小豪 on 2017/3/4.
 */

public class DateUtils {
    public static boolean isSameData(Date data1,Date date2){

        Calendar cal = Calendar.getInstance();
        cal.setTime(data1);
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(date2);

        return cal.get(Calendar.DAY_OF_YEAR)==selectedDate.get(Calendar.DAY_OF_YEAR);
    }
}
