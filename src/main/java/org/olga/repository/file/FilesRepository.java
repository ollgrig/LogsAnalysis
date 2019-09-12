package org.olga.repository.file;

import org.olga.exception.LogsAnalysisException;

import java.nio.file.Path;
import java.util.List;

public interface FilesRepository {
    List<Path> getPaths() throws LogsAnalysisException;
}
