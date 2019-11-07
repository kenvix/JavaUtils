//--------------------------------------------------
// Class LoggingOutputStreamTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.log.Logging;
import com.kenvix.utils.log.LoggingOutputStream;

import java.io.PrintStream;
import java.util.logging.Level;

public class LoggingOutputStreamTest {
    public static void main(String[] args) {
        LoggingOutputStream logStream = new LoggingOutputStream(Logging.getLogger("test"), Level.SEVERE);
        System.setOut(new PrintStream(logStream));
        System.out.println("士大夫士大夫士大夫但是似懂非懂的");
        System.out.println("asdujihasdujias");
    }
}
