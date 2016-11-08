package xiaolei.sun.zhihu_daily.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunxl8 on 2016/11/8.
 */

public class DateUtils {

    public static String getFormatDate(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            String format = sdf.format(date);
            return format;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
