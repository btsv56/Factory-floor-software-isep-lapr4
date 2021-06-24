/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.scm.console.presentation;

import eapli.base.messagesmanagement.application.ImportMessagesController;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Utilizador
 */
public class ImportMessageFilesUI {

    private static final Logger logger = LogManager.getLogger(ImportMessageFilesUI.class);

    public void importMessageFiles() {
        try {
            new ImportMessagesController().importMessages();
        } catch (IOException ex) {
            logger.info("Error opening message file");
        } catch (IllegalArgumentException ex) {
            logger.info("Error creating message");
        } catch (Exception ex) {
            logger.info("Error creating messages");
        }
    }
}
