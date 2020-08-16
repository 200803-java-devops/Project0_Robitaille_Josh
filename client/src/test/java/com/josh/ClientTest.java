package com.josh;

import static org.junit.Assert.*;
import java.util.StringTokenizer;
import org.junit.Test;

public class ClientTest {

    @Test
    public void testTokenizerToGetUsername() {
        String message = new String("NameAccepted:Josh");

        String[] tokens = new String[2];
        StringTokenizer tokenizer = new StringTokenizer(message, ":");
        tokens[0] = tokenizer.nextToken();
        tokens[1] = tokenizer.nextToken();

        String actual = tokens[1];
        String expected = "Josh";

        assertEquals(expected, actual);
    }
}
