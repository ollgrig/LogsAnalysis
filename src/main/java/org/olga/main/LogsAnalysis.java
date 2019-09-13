package org.olga.main;

import org.olga.commons.DateRange;
import org.olga.constant.LogAnalysisConstant;
import org.olga.exception.LogsAnalysisException;
import org.olga.service.LogsService;
import org.olga.service.LogsServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class LogsAnalysis {
    private static LogsService logsService;

    private static LogsService getLogsService() {
        if (isNull(logsService)) {
            logsService = new LogsServiceImpl();
        }
        return logsService;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Input filter parameter for username");
            String username = reader.readLine();
            System.out.println("Input filter parameter for start time period(e.g. 2010-12-03T11:30:00)");
            String startTime = reader.readLine();
            System.out.println("Input filter parameter for end time period(e.g. 2010-12-03T11:30:00)");
            String endTime = reader.readLine();
            System.out.println("Input filter parameter for custom message");//todo pattern?? Does it mean "contains"?
            String customMessage = reader.readLine();
            if (username.isEmpty() && (startTime.isEmpty() && endTime.isEmpty()) && customMessage.isEmpty()) {
                System.out.println("Filter parameters haven't been specified.");
                System.exit(0);
            }

            DateRange timePeriod = new DateRange(startTime,endTime);

            /*List<LocalDateTime> timePeriod = new ArrayList<>();
            if (!startTime.isEmpty() && !endTime.isEmpty()) {
                timePeriod.add(LocalDateTime.parse(startTime));
                timePeriod.add(LocalDateTime.parse(endTime));
            }*/

            System.out.println("Input grouping parameter: \"username\", \"hour\", \"day\", \"month\"");
            String groupingParameter = reader.readLine();
            if (groupingParameter.isEmpty()) {
                System.out.println("Grouping parameters haven't been specified.");
                System.exit(0);
            }

            System.out.println("Input count of threads(default = 1)");
            int countOfThreads;
            String stringCountOfThreads = reader.readLine();
            if (stringCountOfThreads.isEmpty()) {
                countOfThreads = 1;
            } else {
                countOfThreads = Integer.parseInt(stringCountOfThreads);
            }

            System.out.println("Input path or filename to output file");
            String outputFilename = reader.readLine();
            if (outputFilename.isEmpty()) {
                System.out.println("path or filename haven't been specified.");
                outputFilename = System.getProperty(LogAnalysisConstant.USER_DIR) +
                        LogAnalysisConstant.OUTPUT_DIRECTORY + randomAlphanumeric(10) + ".txt";
            }
            System.out.println("********************************************************************");
            getLogsService().printAggregateStatisticsAndWriteFilteredLogsIntoFile(username, timePeriod, customMessage, groupingParameter, outputFilename, countOfThreads);
        } catch (IOException ex) {
            System.out.println("Reading from console has been failed.");
            ex.printStackTrace();
        } catch (LogsAnalysisException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}

