package tests;

import mainpack.TextReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;

/**
 * Created by Shoma on 10.04.2017.
 */
public class TestTextReader {
    public static TextReader textReader;
    ArrayList<String> wordsthis = new ArrayList<>();

    @BeforeAll
    public static void init()
    {
      textReader = new TextReader("C://1//1.txt");
    }

    @Test
    public void testReadFromFile()
    {
        textReader.start();
            wordsthis = textReader.getWords();

    }
    @Test
    public void testAddToSet()
    {

    }

    @Test
    public void testSymbContaint()
    {

    }

    public void testDelPunct()
    {

    }
}
