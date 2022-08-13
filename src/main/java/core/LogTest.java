package core;

import java.io.IOException;
import java.util.logging.*;

public class LogTest {
    private final static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static boolean doAppend = true;

    private LogTest() { }

    public static void setupLogger() {
        LogManager.getLogManager().reset();
        log.setLevel(Level.ALL);

        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setLevel(Level.SEVERE);
        log.addHandler(consolehandler);

        try {
            FileHandler fileHandler = new FileHandler("LogFile.Log", doAppend);
            fileHandler.setLevel(Level.ALL);
            log.addHandler(fileHandler);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Unable to create/access log file", e);
            e.printStackTrace();
        }
    }

    public static void setDoAppend(boolean doAppend) {
        LogTest.doAppend = doAppend;
    }
}
