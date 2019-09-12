package org.olga.util;

import org.olga.constant.LogAnalysisConstant;

public class LogUtils {
    private LogUtils() {
        throw new IllegalArgumentException("Should not be instantiated");
    }

    public static String[] getSplittedLog(String log) {
        return log.split(LogAnalysisConstant.LOG_PARTS_SEPARATOR);
    }
}
