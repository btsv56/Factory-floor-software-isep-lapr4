/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.application;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.model.general.Description;

/**
 *
 * @author Utilizador
 */
public class CreateErrorNotificationController {
    
    private ErrorNotificationRepository repository= PersistenceContext.repositories().errornotification();

    public ErrorNotification createErrorNotification(String description, String type, String messageType) {
        ErrorNotification errorNotification = new ErrorNotificationFactory().createErrorNotification(Description.valueOf(description), 
                new ErrorNotificationType(Description.valueOf(type)), Description.valueOf(messageType));
        return this.repository.save(errorNotification);
    }   
    
}
