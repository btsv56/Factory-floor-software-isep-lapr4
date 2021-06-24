package eapli.base.app.scm.console.presentation;

import eapli.base.app.scm.console.BaseSCM;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportMessageFiles implements Runnable {

    @Override
    public void run() {
        boolean on=true;
        Thread action;
        while (on) {
            action=new Thread(new ImportMessageFilesAction());
            action.start();
            try {
                TimeUnit.SECONDS.sleep(60);
                action.join();
            } catch (InterruptedException ignore) {
            }
        }
    }
}
