package org.olga.commons;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DateRangeTest {
    private LocalDateTime DEFAULT_START = LocalDateTime.parse("2019-01-21T00:00:00");
    private LocalDateTime DEFAULT_END = LocalDateTime.parse("2019-03-21T00:00:00");

    @Mock
    private DateRange dateRange;

    @Before
    public void init() {
        when(dateRange.isEmpty()).thenCallRealMethod();
    }


    @Test
    public void shouldReturnFalse() {
        when(dateRange.getStartDateTime()).thenReturn(DEFAULT_START);
        when(dateRange.getEndDateTime()).thenReturn(DEFAULT_END);

        boolean result = dateRange.isEmpty();

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueFirstNull() {
        when(dateRange.getStartDateTime()).thenReturn(null);
        when(dateRange.getEndDateTime()).thenReturn(DEFAULT_END);

        boolean result = dateRange.isEmpty();

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueSecondNull() {
        when(dateRange.getStartDateTime()).thenReturn(DEFAULT_START);
        when(dateRange.getEndDateTime()).thenReturn(null);

        boolean result = dateRange.isEmpty();

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueBothNull() {
        when(dateRange.getStartDateTime()).thenReturn(null);
        when(dateRange.getEndDateTime()).thenReturn(null);

        boolean result = dateRange.isEmpty();

        assertTrue(result);
    }
}