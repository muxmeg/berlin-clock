package com.ubs.opsit.interviews.service;

import java.text.ParseException;

/**
 * Basic time converter.
 */
public interface TimeConverter {

    /**
     * Converts time into specified string format.
     *
     * @param aTime time to convert.
     * @return converted formatted time.
     * @throws ParseException in case time can't be parsed.
     */
    String convertTime(String aTime) throws ParseException;

}
