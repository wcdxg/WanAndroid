package com.yuaihen.wcdxg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bruce.Zhou on 2016/4/16.
 */
public class TimeUtils {
    public static final SimpleDateFormat sdfServerDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyy_MM_dd_HHmmss");

    /**
     * 1 清晨、2白天、3夜晚
     * @return
     */
    public static int getDawnDayNight() {
        String time = sdfFileName.format(new Date());
        String hourStr = time.substring(11, 13);
        int hour = Integer.valueOf(hourStr);
        if (hour < 8 && hour >= 3) {
            return 1;
        } else if (hour >= 8 && hour < 19) {
            return 2;
        } else if (hour >= 19 || (hour > 0 && hour < 3)) {
            return 3;
        }
        return 2;
    }
}
