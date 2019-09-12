package org.olga.service;

import org.olga.exception.LogsAnalysisException;

import java.time.LocalDateTime;

public interface LogsService {
    void printAggregateStatisticsAndWriteFilteredLogsIntoFile(String username, LocalDateTime[] timePeriod,
                                                              String customMessage, String groupingParameterName,
                                                              String outputFilename, int countOfThreads)
            throws LogsAnalysisException;
}
