package cn.finull.mm.server.common.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 15:46
 */
public final class DateUtil {

    private DateUtil() {}

    /**
     * 获取指定天数前的时间
     * @return
     */
    public static Date getBeforeDays(int days) {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        now.set(Calendar.DATE,now.get(Calendar.DATE) - days);
        return now.getTime();
    }
}
