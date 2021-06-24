/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.errornotificationmanagement;

import eapli.base.errornotificationmanagement.application.ArchiveErrorNotificationController;
import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import javax.persistence.RollbackException;

/**
 *
 * @author Utilizador
 */
public class ArchiveErrorNotificationUI extends AbstractUI{
    private final ArchiveErrorNotificationController theController= new ArchiveErrorNotificationController();
    
    protected Controller controller() {
        return this.theController;
    }


    @Override
    protected boolean doShow() {
        final Iterable<ErrorNotification> errorNotifications = theController.getUntreatedErrorNotifications();
          final SelectWidget<ErrorNotification> selector = new SelectWidget<>("Error notifications: ", errorNotifications,
                new ErrorNotificationPrinter());
          selector.show();
          final ErrorNotification theErrorNotification=selector.selectedElement();
           if(theErrorNotification == null) return false;
           try{
           theController.archiveErrorNotification(theErrorNotification);
           }catch(RollbackException ex){
               System.out.println("The error notification has already been archived");
           }
          return false;
    }

    @Override
    public String headline() {
        return "Archive error notification";
    }
    
}
