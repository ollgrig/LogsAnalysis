package org.olga.service;

import org.olga.commons.DateRange;
import org.olga.exception.LogsAnalysisException;

public interface LogsService {
    void printAggregateStatisticsAndWriteFilteredLogsIntoFile(String username, DateRange timePeriod,
                                                              String customMessage, String groupingParameterName,
                                                              String outputFilename, int countOfThreads)
            throws LogsAnalysisException;
}
