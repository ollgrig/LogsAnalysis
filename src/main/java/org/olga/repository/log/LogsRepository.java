package org.olga.repository.log;

import org.olga.commons.DateRange;
import org.olga.exception.LogsAnalysisException;

import java.util.List;

public interface LogsRepository {
    List<String> getLogsFromFile(int countOfTreads, String username, DateRange timePeriod, String customMessage) throws LogsAnalysisException;

}
