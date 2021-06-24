package eapli.base.app.scm.console.messagelogging;

import eapli.framework.util.Calendars;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageLoggerToFile {

    File file;
    FileWriter fr;

    public MessageLoggerToFile(String path) throws IOException {
        file=new File("./MessageLog/"+path);
        fr=new FileWriter(file, true);
    }

    public void writeId(String id) throws IOException {
        Calendar now=Calendars.now();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        fr.write(sdf.format(now.getTime())+"\nMachine ID: "+id+"\n");
    }

    public void messageLog(String message) {
        try {
            fr.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileReader() throws IOException {
        fr.write("\n\n");
        fr.close();
    }
}
