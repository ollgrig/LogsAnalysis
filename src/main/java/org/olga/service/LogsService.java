package org.olga.service;

import org.olga.exception.LogsAnalysisException;

import java.time.LocalDateTime;
import java.util.List;

public interface LogsService {
    void printAggregateStatisticsAndWriteFilteredLogsIntoFile(String username, List<LocalDateTime> timePeriod,
                                                              String customMessage, String groupingParameterName,
                                                              String outputFilename, int countOfThreads)
            throws LogsAnalysisException;
}
