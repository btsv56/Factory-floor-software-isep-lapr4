/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class CSVImporter implements MessagesImporter {

    @Override
    public BufferedReader begin(File file) throws IOException {
        return new BufferedReader(new FileReader(file));
    }

    @Override
    public List<String> element(BufferedReader stream) throws IOException {
        String line = stream.readLine();
        return line!=null ? Arrays.asList(line.split(";")):null;
    }

    @Override
    public void end(BufferedReader stream) throws IOException {
        stream.close();
    }

}
