package com.ht.bim.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.format.datetime.DateFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhong on 2018/7/8.
 */
public class MultiStyleDateFormatter extends DateFormatter {

    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMMDD_SLASHED = "yyyy/MM/dd";
    private static final String YYYYMMDD_HHMMSS_SLASHED = "yyyy/MM/dd HH:mm:ss";

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        boolean hasDash = text.contains("-");
        boolean hasSlash = text.contains("/");
        boolean hasHms = text.contains(":");
        String formatter = null;
        if (hasDash) {
            formatter = hasHms ? YYYYMMDD_HHMMSS : YYYYMMDD;
        } else if (hasSlash) {
            formatter = hasHms ? YYYYMMDD_HHMMSS_SLASHED : YYYYMMDD_SLASHED;
        }
        if (StringUtils.isNotEmpty(formatter)) {
            SimpleDateFormat format = new SimpleDateFormat(formatter, locale);
            return format.parse(text);
        } else {
            return super.parse(text, locale);
        }
    }

    @Override
    public String print(Date date, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat(YYYYMMDD_HHMMSS, locale);
        return format.format(date);
    }
}
