/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public interface MessagesImporter {

    BufferedReader begin(File file) throws IOException;
    
    List<String> element(BufferedReader stream) throws IOException;

    void end(BufferedReader stream) throws IOException;

}
