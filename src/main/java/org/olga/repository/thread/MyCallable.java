package org.olga.repository.thread;

import org.olga.commons.DateRange;
import org.olga.exception.LogsAnalysisException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.olga.util.LogUtils.getSplittedLog;

public class MyCallable implements Callable<List<String>> {
    private Path path;
    private String username;
    private DateRange timePeriod;
    private String customMessage;

    public MyCallable(Path path, String username, DateRange timePeriod, String customMessage) {
        this.path = path;
        this.username = username;
        this.timePeriod = timePeriod;
        this.customMessage = customMessage;
    }

    @Override
    public List<String> call() throws LogsAnalysisException {
        try {
            return Files.lines(path)
                    .filter(log -> {
                        if (!timePeriod.isEmpty()) {
                            LocalDateTime dateTime = LocalDateTime.parse(getSplittedLog(log)[0]);
                            return dateTime.isAfter(timePeriod.getStartDateTime()) && dateTime.isBefore(timePeriod.getEndDateTime());
                        }
                        return true;
                    })
                    .filter(log -> filterLogLine(log, username, 1))
                    .filter(log -> filterLogLine(log, customMessage, 2))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new LogsAnalysisException("Thread can't read line from file", e);
        }
    }

    private Boolean filterLogLine(String log, String filterParameter, int partOfLog) {
        return filterParameter.isEmpty() ||
                getSplittedLog(log)[partOfLog].equals(filterParameter);
    }
}
