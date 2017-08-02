package com.ubs.opsit.interviews.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility to parse time.
 */
public final class TimeParserUtil {

    private TimeParserUtil() {
    }

    private static final String TIME_FORMAT = "hh:mm:ss";
    private static final DateFormat dateFormatter = new SimpleDateFormat(TIME_FORMAT);

    /**
     * Parse time from string into Calendar.
     *
     * @param time time to parse.
     * @return Calendar with parsed time.
     * @throws ParseException in case time can't be parsed.
     */
    public static Calendar parseTime(String time) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormatter.parse(time));
        return calendar;
    }
}
