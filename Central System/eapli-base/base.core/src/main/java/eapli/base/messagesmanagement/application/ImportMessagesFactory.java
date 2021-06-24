/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.AppSettings;
import eapli.base.Application;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilizador
 */
public class ImportMessagesFactory {

    private static final AppSettings SETTINGS = Application.settings();

    public static MessagesImporter build(String format) {
        MessagesImporter importer = null;
        try {
            importer = (MessagesImporter) Class.forName(SETTINGS.getProperty(format)).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException ex) {
            try {
                importer = (MessagesImporter) Class.forName(SETTINGS.getCSVFormat()).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex1) {
                Logger.getLogger(ImportMessagesFactory.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        return importer;
    }
}
