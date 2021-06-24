/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.application;

import eapli.base.errornotificationmanagement.application.CreateErrorNotificationController;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Utilizador
 */
public class ImportMessageService {

    private static final Logger logger = LogManager.getLogger(ImportMessageService.class);

    public void importFile(File file, MessagesImporter importer) throws IOException {
        final CreateMessageService createMessageService = new CreateMessageService();
        List<String> attributes;
        BufferedReader stream = null;
        try {
            stream = importer.begin(file);
            attributes = importer.element(stream);
            while (attributes != null) {
                try {
                    createMessageService.createMessage(attributes.get(1).trim(), attributes);
                } catch (IllegalStateException ex) {
                    new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), ex.getMessage(), attributes.get(1).trim());
                }catch(ArrayIndexOutOfBoundsException ex2) {
                    logger.error("Error importing a message");
                }catch(NoResultException ex3) {
                    new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Lot doesn't exist", attributes.get(1).trim());
                }catch(DateTimeParseException|IllegalArgumentException ex4) {
                    new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Wrong date hour", attributes.get(1).trim());
                }catch(Exception ex4) {
                    new CreateErrorNotificationController().createErrorNotification(StringUtils.join(attributes, ';'), "Wrong format", attributes.get(1).trim());
                }
                attributes = importer.element(stream);
            }
        } catch (IOException ex) {
            logger.error("Error importing messages", ex);
            throw ex;
        } finally {
            importer.end(stream);
        }
    }
}
