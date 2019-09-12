package org.olga.repository.file;

import org.olga.exception.LogsAnalysisException;
import org.olga.constant.LogAnalysisConstant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesRepositoryImpl implements FilesRepository {

    @Override
    public List<Path> getPaths() throws LogsAnalysisException {
        try (Stream<Path> paths = Files.walk(Paths.get(System.getProperty(LogAnalysisConstant.USER_DIR) + LogAnalysisConstant.INPUT_DIRECTORY))) {
            return paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new LogsAnalysisException("Walking the file tree are failed", e);
        }
    }
}
