package org.olga.service;

import org.olga.commons.DateRange;
import org.olga.constant.LogAnalysisConstant;
import org.olga.exception.LogsAnalysisException;
import org.olga.repository.log.LogsRepository;
import org.olga.repository.log.LogsRepositoryImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.olga.util.LogUtils.getSplittedLog;


public class LogsServiceImpl implements LogsService {
    private LogsRepository logsRepository;

    @Override
    public void printAggregateStatisticsAndWriteFilteredLogsIntoFile(String username, DateRange timePeriod,
                                                                     String customMessage, String groupingParameterName,
                                                                     String outputFilename, int countOfThreads)
            throws LogsAnalysisException {
        printAggregateStatistics(groupByParameterIntoMap(groupingParameterName, countOfThreads, username, timePeriod, customMessage), groupingParameterName);
        writeFilteredLogsIntoFile(outputFilename, countOfThreads, username, timePeriod, customMessage);
    }

    private void printAggregateStatistics(Map<String, List<String>> groupingMap, String groupingParameterName) {
        System.out.println(groupingParameterName + "      -     Count of records");
        for (Map.Entry<String, List<String>> entry : groupingMap.entrySet()) {
            System.out.println(entry.getKey() + "      -     " + entry.getValue().size());
        }
    }

    private void writeFilteredLogsIntoFile(String outputFilename, int countOfThreads, String username, DateRange timePeriod, String customMessage) throws LogsAnalysisException {
        Path out = Paths.get(outputFilename);
        try {
            Files.write(out, getLogsRepository().getLogsFromFile(countOfThreads, username, timePeriod, customMessage), Charset.defaultCharset());
        } catch (IOException e) {
            throw new LogsAnalysisException("Writing to file has been failed", e);
        }
    }

    private Map<String, List<String>> groupByParameterIntoMap(String groupingParameterName, int countOfThreads, String username, DateRange timePeriod, String customMessage) throws LogsAnalysisException {
        Function<String, String> mapper;
        switch (groupingParameterName) {
            case "username":
                mapper = string -> getSplittedLog(string)[1];
                break;
            case "hour":
                mapper = string -> {
                    LocalDateTime dateTime = LocalDateTime.parse(getSplittedLog(string)[0]);
                    return dateTime.format(DateTimeFormatter.ofPattern(LogAnalysisConstant.HOUR_PATTERN));
                };
                break;
            case "day":
                mapper = string -> {
                    LocalDateTime dateTime = LocalDateTime.parse(getSplittedLog(string)[0]);
                    return dateTime.format(DateTimeFormatter.ofPattern(LogAnalysisConstant.DAY_PATTERN));
                };
                break;
            case "month":
                mapper = string -> {
                    LocalDateTime dateTime = LocalDateTime.parse(getSplittedLog(string)[0]);
                    return dateTime.format(DateTimeFormatter.ofPattern(LogAnalysisConstant.MONTH_PATTERN));
                };
                break;
            default:
                throw new IllegalArgumentException("Unknown parameter");
        }
        return getLogsRepository().getLogsFromFile(countOfThreads, username, timePeriod, customMessage).stream()
                .collect(Collectors.groupingBy(mapper));
    }

    private LogsRepository getLogsRepository() {
        if (Objects.isNull(logsRepository)) {
            logsRepository = new LogsRepositoryImpl();
        }
        return logsRepository;
    }
}
