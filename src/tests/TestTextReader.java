package tests;

import mainpack.TextReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Created by Shoma on 10.04.2017.
 */
public class TestTextReader {
    public static TextReader textReader;
    ArrayList<String> words = new ArrayList<>();
    @BeforeAll
    public static void init()
    {
      textReader = new TextReader("C:\1\2.txt");
    }

    @Test
    public void testRun()
    {
        textReader.run();
        words = textReader.getWords();

    }
}
