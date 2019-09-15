package org.olga.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LogUtilsTest {
    private static final String DEFAULT_LOG = "log;log;log";

    @Test
    public void shouldReturnSplittedString() {
        String[] expected = {"log", "log", "log"};

        String[] result = LogUtils.getSplittedLog(DEFAULT_LOG);

        assertEquals("Verify array length", expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals("Verify array element " + i, expected[i], result[i]);
        }
    }
}