/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Utilizador
 */
public class ImportMessagesController {

    private final ManageFilesService manageFilesService = new ManageFilesService();

    public void importMessages() throws IOException, InterruptedException {
        List<File> fileList = manageFilesService.allMessageFiles();
        List<Thread> listThreads = new ArrayList<>();
        for (File f : fileList) {
            final ImportMessageService importMessageService = new ImportMessageService();
            final MessagesImporter messageImporter = ImportMessagesFactory.build(FilenameUtils.getExtension(f.getName()));
            Thread t = createThread(importMessageService, f, messageImporter);
            t.start();
            listThreads.add(t);
        }
        waitThreads(fileList, fileList.size(), listThreads);
        
    }

    private Thread createThread(ImportMessageService importMessageService, File f, MessagesImporter messageImporter) {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    importMessageService.importFile(f, messageImporter);
                } catch (IOException ex) {
                    Logger.getLogger(ImportMessagesController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        return t;
    }
    
    private void waitThreads(List<File>fileList,int n,List<Thread> listThreads) throws IOException, InterruptedException {
        for(int i=0;i<n;i++) {
            listThreads.get(i).join();
        }
        manageFilesService.moveReadFiles(fileList);
    }
}
