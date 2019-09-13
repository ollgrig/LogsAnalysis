package org.olga.commons;

import org.olga.exception.LogsAnalysisException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class DateRange {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public DateRange(String start, String end) throws LogsAnalysisException {
        if (!start.isEmpty() && !end.isEmpty()) {
            try {
                this.startDateTime = LocalDateTime.parse(start);
                this.endDateTime = LocalDateTime.parse(end);
            }catch (DateTimeParseException e){
                throw new IllegalArgumentException("Illegal pattern for date",e);
            }

        }
    }
    public boolean isEmpty(){
        return Objects.isNull(startDateTime) || Objects.isNull(endDateTime);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
