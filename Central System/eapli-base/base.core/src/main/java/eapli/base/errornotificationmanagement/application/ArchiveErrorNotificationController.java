/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.application;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.application.Controller;

/**
 *
 * @author Utilizador
 */
public class ArchiveErrorNotificationController implements Controller {
    
    private ErrorNotificationRepository repository= PersistenceContext.repositories().errornotification();
    
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        return repository.getUntreatedErrorNotifications();   
    }
    
    public void archiveErrorNotification(ErrorNotification errorNotification) {
        errorNotification.archive();
        repository.save(errorNotification);
    }
}
