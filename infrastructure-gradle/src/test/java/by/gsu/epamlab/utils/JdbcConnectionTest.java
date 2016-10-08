package by.gsu.epamlab.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class JdbcConnectionTest {

    @Test
    public void coreutilsTest() {
        String expected = "SELECT idTask, title, date, fileName FROM tasks WHERE userId=(?) AND status='todo' ORDER BY date";
        String actual = ActiveTaskDecoder.fromString("today").getSqlStrinq();
        assertEquals(expected, actual);
    }
}
