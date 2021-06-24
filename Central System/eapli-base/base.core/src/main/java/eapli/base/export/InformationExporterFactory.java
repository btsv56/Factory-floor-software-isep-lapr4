/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export;

import java.util.logging.Level;
import java.util.logging.Logger;

import eapli.base.AppSettings;
import eapli.base.Application;
import eapli.base.messagesmanagement.application.ImportMessagesFactory;

/**
 *
 * @author Utilizador
 */
public class InformationExporterFactory {

    private static final AppSettings SETTINGS = Application.settings();

    public InformationExporter build(String format) {
        String formatTemp=format.substring(format.length()-3).toLowerCase();
        InformationExporter exporter=null;
        try {
            exporter = (InformationExporter) Class.forName(SETTINGS.getProperty(formatTemp)).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NullPointerException ex) {
            Logger.getLogger(ImportMessagesFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exporter;
    }
}
