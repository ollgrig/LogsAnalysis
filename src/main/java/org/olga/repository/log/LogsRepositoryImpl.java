package org.olga.repository.log;

import org.olga.commons.DateRange;
import org.olga.exception.LogsAnalysisException;
import org.olga.repository.file.FilesRepository;
import org.olga.repository.file.FilesRepositoryImpl;
import org.olga.repository.thread.MyCallable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class LogsRepositoryImpl implements LogsRepository {
    private FilesRepository filesRepository;

    private FilesRepository getFilesRepository() {
        if (isNull(filesRepository)) {
            filesRepository = new FilesRepositoryImpl();
        }
        return filesRepository;
    }

    @Override
    public List<String> getLogsFromFile(int countOfTreads, String username, DateRange timePeriod, String customMessage) throws LogsAnalysisException {
        ExecutorService executorService = Executors.newFixedThreadPool(countOfTreads);
        try {
            List<Future<List<String>>> futureList = getFilesRepository().getPaths().stream()
                    .map(path -> new MyCallable(path, username, timePeriod, customMessage))
                    .map(executorService::submit)
                    .collect(Collectors.toList());

            List<List<String>> listOfListOfLogs = new ArrayList<>();
            for (Future<List<String>> future : futureList) {
                listOfListOfLogs.addAll(Collections.singleton(future.get()));
            }

            return listOfListOfLogs.stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            throw new LogsAnalysisException("Can't get result of work of threads", e);
        } finally {
            executorService.shutdown();
        }
    }
}
