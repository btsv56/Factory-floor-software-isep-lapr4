/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.errornotificationmanagement.domain.StateEnum;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;

/**
 *
 * @author Utilizador
 */
public class JpaErrorNotificationRepository extends BasepaRepositoryBase<ErrorNotification, Long, Long>
        implements ErrorNotificationRepository {

    public JpaErrorNotificationRepository() {
        super("id");
    }

    public Iterable<ErrorNotification> findByTime() {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e From ErrorNotification e where e.errorNotificationState.stateError = :state order by e.dateTime",
                ErrorNotification.class);
        query.setParameter("state", StateEnum.ARCHIVED);
        return query.getResultList();
    }

    public Iterable<ErrorNotification> findByMachine(final Machine machine) {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e from ErrorNotification e where e.machine = :machine and e.errorNotificationState.stateError = :state",
                ErrorNotification.class);
        query.setParameter("state", StateEnum.ARCHIVED);
        query.setParameter("machine", machine);
        return query.getResultList();
    }

    @Override
    public Iterable<ErrorNotification> getUntreatedErrorNotifications() {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery(
                "Select e from ErrorNotification e where e.errorNotificationState.stateError = :state",
                ErrorNotification.class);
        query.setParameter("state", StateEnum.UNTREATED);
        return query.getResultList();
    }

    @Override
    public Iterable<ErrorNotification> getErrorNotificationByFilter(Iterable<ProductionLine> productionLines,
            Iterable<ErrorNotificationType> types) {
        String queryString = "Select e from ErrorNotification e ";
        if (productionLines != null) {
            queryString += "where ";
            for (ProductionLine line : productionLines) {
                if (line != null) {
                    for (int j = 0; j < line.getMachines().size(); j++) {
                        queryString += "e.machine=:machine" + j + (j != line.getMachines().size() - 1 ? " OR " : "");
                    }
                }
            }
        }
        int j = 0;
        if (types != null) {
            if (productionLines == null)
                queryString += "where ";
            for (ErrorNotificationType type : types) {
                if (type != null) {
                    queryString += (productionLines == null ? "" : " OR ") + " e.errorNotificationType=:type" + j
                            + (productionLines == null ? " OR " : "");
                    j++;
                }
            }
        }

        final TypedQuery<ErrorNotification> query = entityManager().createQuery(queryString, ErrorNotification.class);
        int i = 0;
        if (productionLines != null)
            for (ProductionLine line : productionLines) {
                if (line != null) {
                    for (Machine m : line.getMachines()) {
                        query.setParameter("machine" + i, m);
                        i++;
                    }
                }
            }
        i = 0;
        if (types != null)
            for (ErrorNotificationType type : types) {
                if (type != null) {
                    query.setParameter("type" + i, type);
                    i++;
                }
            }
        return query.getResultList();
    }

    @Override
    public Iterable<ErrorNotificationType> getErrorNotificationTypes() {
        final TypedQuery<ErrorNotification> query = entityManager().createQuery("Select e from ErrorNotification e",
                ErrorNotification.class);

        List<ErrorNotificationType> list = new LinkedList<>();
        for (ErrorNotification error : query.getResultList()) {
            if (!list.contains(error.type()))
                list.add(error.type());
        }
        return list;
    }

}
