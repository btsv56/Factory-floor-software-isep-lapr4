package eapli.base.app.scm.console.errorlogging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ErrorLoggerToFile {

    public static final Logger LOGGER = Logger.getLogger(ErrorLoggerToFile.class.getName());
    FileHandler fh;

    public ErrorLoggerToFile(String path) throws IOException {
        fh=new FileHandler("./Log/"+path, true);
    }

    public void errorLog(String message) {
        try {
            // This block configure the logger with handler and formatter
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            LOGGER.info(message);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
