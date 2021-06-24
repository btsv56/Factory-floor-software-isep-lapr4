package eapli.base.errornotificationmanagement.application;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.application.Controller;

public class CheckErrorNotificationsController implements Controller {

    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLine();
    private final ErrorNotificationRepository errorReporitory = PersistenceContext.repositories().errornotification(); 

    public Iterable<ProductionLine> listProductionLines(){
        return productionLineRepository.findAll();
    }

    public Iterable<ErrorNotificationType> listErrorNotificationTypes(){
        return errorReporitory.getErrorNotificationTypes();
    }

    public Iterable<ErrorNotification> listErrorNotificatoinByFilter(Iterable<ProductionLine> productionLines, Iterable<ErrorNotificationType> types){
        return errorReporitory.getErrorNotificationByFilter(productionLines, types);
    }
    
}