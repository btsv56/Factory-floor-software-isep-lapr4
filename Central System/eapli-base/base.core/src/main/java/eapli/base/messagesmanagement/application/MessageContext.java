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
public class MessageContext {

    private static final AppSettings SETTINGS = Application.settings();

    public static MessageFactory messageFactory(String format) {
        MessageFactory importer=null;
        try {
            importer = (MessageFactory) Class.forName(SETTINGS.getProperty(format)).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ImportMessagesFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return importer;
    }
}
