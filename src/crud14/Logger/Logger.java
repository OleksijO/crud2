package crud14.Logger;

import crud14.spring.Context;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logs most general operations
 */
public class Logger {

    private static File logfile = null;
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private static final SimpleDateFormat logFileNameFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static boolean logON=true;
    private static final int logFileMaxSize =100000;

    public static void setServletContext(ServletContext servletContext) {
                checkLogfile();
    }

    public static void log(String message) {
        if (!logON) return;
        checkLogfile();
        try (
                FileWriter fileWriter = new FileWriter(logfile, true)) {
            fileWriter.write(dataFormat.format(new Date()) + "\t" + message + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
            logOFF();
        }
    }

    private static void checkLogfile() {
        if (logfile == null) {
            createNewLogFile();
        }
        if (logfile.exists() && logfile.length() > logFileMaxSize) {
            createNewLogFile();
        }
    }

    private static void createNewLogFile() {
        logfile = new File(getNewFileName());
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("\t LOG FILE AT PATH:  " + logfile.getAbsolutePath());
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    private static Path getLogsDir() {
        Path logDir = Paths.get(Context.getExternalContext().getRealPath("/") + "/logs");
        if (!logDir.toFile().exists()) {
            try {
                Files.createDirectory(logDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logDir;
    }

    private static String getNewFileName() {
        return getLogsDir() + "/" + "LOG_" + logFileNameFormat.format(new Date()) + ".txt";
    }

    private static void logOFF(){
        logON=false;
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("\t ERROR: LOGGING IS OFF DUE TO IOEXCEPTIONS!");
        System.out.println("-----------------------------------------------------------------------------------------");

    }
}
