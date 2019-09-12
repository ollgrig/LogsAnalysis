package org.olga.repository.log;

import org.olga.exception.LogsAnalysisException;

import java.time.LocalDateTime;
import java.util.List;

public interface LogsRepository {
    List<String> getLogsFromFile(int countOfTreads, String username, LocalDateTime[] timePeriod, String customMessage) throws LogsAnalysisException;

}
