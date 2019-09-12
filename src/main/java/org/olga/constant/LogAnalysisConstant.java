package org.olga.constant;

public class LogAnalysisConstant {
    public static final String INPUT_DIRECTORY = "\\src\\main\\resources\\input\\";
    public static final String OUTPUT_DIRECTORY = "\\src\\main\\resources\\output\\";
    public static final String LOG_PARTS_SEPARATOR = ";";
    public static final String USER_DIR = "user.dir";
    public static final String HOUR_PATTERN = "yyyy-MM-dd-HH";
    public static final String DAY_PATTERN = "yyyy-MM-dd";
    public static final String MONTH_PATTERN = "yyyy-MM";

    private LogAnalysisConstant(){
        throw new IllegalArgumentException("Should not be instantiated");
    }
}
