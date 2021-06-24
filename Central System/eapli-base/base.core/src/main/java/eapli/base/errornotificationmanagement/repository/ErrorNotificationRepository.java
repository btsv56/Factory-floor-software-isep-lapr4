/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.repository;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.repositories.DomainRepository;

/**
 *
 * @author Utilizador
 */
public interface ErrorNotificationRepository extends DomainRepository<Long, ErrorNotification>{
    Iterable<ErrorNotification> findByTime();
    Iterable<ErrorNotification> findByMachine(Machine machine);
    public Iterable<ErrorNotification> getUntreatedErrorNotifications();
	Iterable<ErrorNotification> getErrorNotificationByFilter(Iterable<ProductionLine> productionLines,
            Iterable<ErrorNotificationType> types);
    Iterable<ErrorNotificationType> getErrorNotificationTypes();
}
