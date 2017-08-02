package com.ubs.opsit.interviews.service.impl;

import com.ubs.opsit.interviews.model.LampType;
import com.ubs.opsit.interviews.service.TimeConverter;
import com.ubs.opsit.interviews.util.TimeParserUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses and converts time into Berlin Clock format.
 */
public class BerlinClockTimeConverter implements TimeConverter {

    private static final String CASE_24_HRS = "24:00:00";
    private static final String CASE_24_HRS_RESULT = "Y\r\nRRRR\r\nRRRR\r\nOOOOOOOOOOO\r\nOOOO";

    private static final String LENGTH = "length";
    private static final String DELIMITER = "delimiter";

    private static final String LINE_SEPARATOR = "\r\n";
    private static final String FOURTH_LINE_REPLACE_REGEXP = LampType.YELLOW_LIGHT.getSymbol()
            + LampType.YELLOW_LIGHT.getSymbol() + LampType.YELLOW_LIGHT.getSymbol();
    private static final String FOURTH_LINE_REPLACE_VALUE = LampType.YELLOW_LIGHT.getSymbol()
            + LampType.YELLOW_LIGHT.getSymbol() + LampType.RED_LIGHT.getSymbol();

    private static final Map<Integer, Map<String, Integer>> linesData; //contains constants for lines displaying

    static {
        linesData = new HashMap<>();
        Map<String, Integer> tempMap = new HashMap<>();
        tempMap.put(DELIMITER, 2);
        linesData.put(1, tempMap);

        tempMap = new HashMap<>();
        tempMap.put(LENGTH, 4);
        tempMap.put(DELIMITER, 5);
        linesData.put(2, tempMap);

        tempMap = new HashMap<>();
        tempMap.put(LENGTH, 4);
        tempMap.put(DELIMITER, 5);
        linesData.put(3, tempMap);

        tempMap = new HashMap<>();
        tempMap.put(LENGTH, 11);
        tempMap.put(DELIMITER, 5);
        linesData.put(4, tempMap);

        tempMap = new HashMap<>();
        tempMap.put(LENGTH, 4);
        tempMap.put(DELIMITER, 5);
        linesData.put(5, tempMap);
    }

    @Override
    public String convertTime(String aTime) throws ParseException {
        if (aTime.equals(CASE_24_HRS)) { //corner case not supported by date parser
            return CASE_24_HRS_RESULT;
        }
        Calendar parsedTime = TimeParserUtil.parseTime(aTime);

        return timeToBerlin(parsedTime.get(Calendar.HOUR_OF_DAY), parsedTime.get(Calendar.MINUTE),
                parsedTime.get(Calendar.SECOND));
    }

    private String timeToBerlin(int hours, int minutes, int seconds) {
        return getFirstLine(seconds) + LINE_SEPARATOR + getSecondLine(hours) + LINE_SEPARATOR + getThirdLine(hours) +
                LINE_SEPARATOR + getFourthLine(minutes) + LINE_SEPARATOR + getFifthLine(minutes);
    }

    private String getFirstLine(int seconds) {
        return seconds % linesData.get(1).get(DELIMITER) == 0 ? LampType.YELLOW_LIGHT.getSymbol()
                : LampType.UNLIT.getSymbol();
    }

    private String getSecondLine(int hours) {
        Map<String, Integer> lineData = linesData.get(2);
        return processLine(hours / lineData.get(DELIMITER), lineData.get(LENGTH), LampType.RED_LIGHT);
    }

    private String getThirdLine(int hours) {
        Map<String, Integer> lineData = linesData.get(3);
        return processLine(hours % lineData.get(DELIMITER), lineData.get(LENGTH), LampType.RED_LIGHT);
    }

    private String getFourthLine(int minutes) {
        Map<String, Integer> lineData = linesData.get(4);
        return processLine(minutes / lineData.get(DELIMITER), lineData.get(LENGTH), LampType.YELLOW_LIGHT)
                .replaceAll(FOURTH_LINE_REPLACE_REGEXP, FOURTH_LINE_REPLACE_VALUE);
    }

    private String getFifthLine(int minutes) {
        Map<String, Integer> lineData = linesData.get(5);
        return processLine(minutes % lineData.get(DELIMITER), lineData.get(LENGTH), LampType.YELLOW_LIGHT);
    }

    private String processLine(int litLamps, int totalLamps, LampType litLampType) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < totalLamps; i++) {
            result.append(i < litLamps ? litLampType.getSymbol() : LampType.UNLIT.getSymbol());
        }
        return result.toString();
    }
}