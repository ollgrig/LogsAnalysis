package org.olga.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LogUtilsTest {
    private static final String DEFAULT_LOG_PARTS_SEPARATOR = ";";
    private static final String DEFAULT_LOG = "log;log;log";
    private static final String[] DEFAULT_LOG_ARRAY = new String[3];

    @Test
    public void shouldReturnSplittedString() {
        String[] expected = {"log", "log", "log"};

        String[] result = DEFAULT_LOG.split(DEFAULT_LOG_PARTS_SEPARATOR);

        assertEquals("Verify array length", expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Verify array element " + i, expected[i], result[i]);
        }
    }
}