/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.Application;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class ManageFilesService {

    public List<File> allMessageFiles() {
        List<File> fileNames = new ArrayList<>();
        final File folder = new File(Application.settings().getMessageEntryFolder());
        for (final File fileEntry : folder.listFiles()) {
            fileNames.add(fileEntry);
        }
        return fileNames;
    }

    public void moveReadFiles(List<File> files) throws IOException {
        for (File f : files) {
            final String processedFolder = String.format("%s%s",Application.settings().getMessageOutFolder(),f.getName());
            Files.move(Paths.get(f.getAbsolutePath()), Paths.get(processedFolder));

        }
    }

}
