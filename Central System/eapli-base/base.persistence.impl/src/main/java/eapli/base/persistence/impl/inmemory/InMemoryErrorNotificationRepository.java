/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import java.util.LinkedList;
import java.util.List;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.errornotificationmanagement.domain.StateEnum;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author Utilizador
 */
public class InMemoryErrorNotificationRepository extends InMemoryDomainRepository<Long, ErrorNotification>
        implements ErrorNotificationRepository {

    static {
        InMemoryInitializer.init();
    }

    public Iterable<ErrorNotification> findByType(ErrorNotificationType type) {
        return match(e -> e.hasNotificationType(type));
    }

    @Override
    public Iterable<ErrorNotification> findByTime() {
        return findAll();
    }

    @Override
    public Iterable<ErrorNotification> findByMachine(Machine machine) {
        return match(e -> e.hasMachine(machine));
    }

    @Override
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        return match(e -> e.hasState(StateEnum.UNTREATED));
    }

    @Override
    public Iterable<ErrorNotification> getErrorNotificationByFilter(Iterable<ProductionLine> productionLines,
            Iterable<ErrorNotificationType> types) {
        return match(e -> e.hasAnyNotificationType(types) && e.hasAnyProductionLine(productionLines));
    }

    @Override
    public Iterable<ErrorNotificationType> getErrorNotificationTypes() {
        List<ErrorNotificationType> list = new LinkedList<>();
        for (ErrorNotification error : findAll()) {
            list.add(error.type());
        }
        return list;
    }
}
