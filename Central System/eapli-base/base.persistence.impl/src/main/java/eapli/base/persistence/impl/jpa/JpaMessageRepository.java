/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.messagesmanagement.domain.MessageType;
import eapli.base.messagesmanagement.repositories.MessageRepository;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.model.general.Description;

import java.time.LocalDateTime;

import javax.persistence.TypedQuery;

/**
 *
 * @author Utilizador
 */
public class JpaMessageRepository extends BasepaRepositoryBase<Message, Long, Long> implements MessageRepository {

    public JpaMessageRepository() {
        super("id");
    }

    @Override
    public Message getStartOfActivityOf(ProductionOrder order, Machine machine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m where m.id IN (SELECT s.id FROM StartOfActivityMessage s WHERE s.productionOrder=:po AND s.machine=:ma)",
                Message.class);
        query.setParameter("po", order);
        query.setParameter("ma", machine);

        return query.getSingleResult();
    }

    @Override
    public Message getEndOfActivityOf(ProductionOrder order, Machine machine, LocalDateTime time) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m where m.id IN (SELECT s.id FROM EndOfActivityMessage s WHERE s.productionOrder.internalCode=:po OR s.dateHour>:t AND s.machine.internalCode=:ma ORDER BY s.dateHour ASC)",
                Message.class);
        query.setParameter("po", order.getInternalCode());
        query.setParameter("ma", machine.identity());
        query.setParameter("t", time);
        return query.getSingleResult();
    }

    @Override
    public Iterable<Message> getOutputDeliveryMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity,
            Machine fromMachine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m where m.id IN (SELECT m1.id FROM OutputDeliveryMessage m1 WHERE m1.machine.internalCode=:ma AND m1.dateHour BETWEEN :sa AND :ea)",
                Message.class);
        query.setParameter("ma", fromMachine.identity());
        query.setParameter("sa", startOfActivity);
        query.setParameter("ea", endOfActivity);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> getReversalMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity,
            Machine fromMachine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m where m.id IN (SELECT m1.id FROM ReversalMessage m1 WHERE m1.machine.internalCode=:ma AND m1.dateHour BETWEEN :sa AND :ea)",
                Message.class);
        query.setParameter("ma", fromMachine.identity());
        query.setParameter("sa", startOfActivity);
        query.setParameter("ea", endOfActivity);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> getConsumptionMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity,
            Machine fromMachine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m where m.id IN (SELECT m1.id FROM ConsumptionMessage m1 WHERE m1.machine.internalCode=:ma AND m1.dateHour BETWEEN :sa AND :ea)",
                Message.class);
        query.setParameter("ma", fromMachine.identity());
        query.setParameter("sa", startOfActivity);
        query.setParameter("ea", endOfActivity);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> getOutputMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity,
            Machine fromMachine) {
        final TypedQuery<Message> query = entityManager().createQuery(
                "SELECT m FROM Message m WHERE m.id IN (SELECT m1.id FROM OutputMessage m1 WHERE m1.machine.internalCode=:ma AND m1.dateHour BETWEEN :sa AND :ea)",
                Message.class);
        query.setParameter("ma", fromMachine.identity());
        query.setParameter("sa", startOfActivity);
        query.setParameter("ea", endOfActivity);
        return query.getResultList();
    }

    @Override
    public Iterable<Message> getTimeMessagesOrderedByTimeBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity,
            Machine fromMachine) {
            final TypedQuery<Message> query = entityManager()
                    .createQuery("SELECT m FROM Message m " + 
                    "WHERE m.machine.internalCode=:ma AND " + 
                    "m.dateHour BETWEEN :sa AND :ea AND" +
                    "(m.type=:rat OR m.type=:fst OR m.type=:eat OR m.type=:sat) " + 
                    "ORDER BY m.dateHour ASC "
                    , Message.class);
            query.setParameter("ma", fromMachine.identity());
            query.setParameter("sa", startOfActivity);
            query.setParameter("ea", endOfActivity);
            query.setParameter("rat", Description.valueOf(MessageType.RESUME_OF_ACTIVITY));
            query.setParameter("fst", Description.valueOf(MessageType.FORCED_STOP));
            query.setParameter("eat", Description.valueOf(MessageType.END_OF_ACTIVITY));
            query.setParameter("sat", Description.valueOf(MessageType.START_OF_ACTIVITY));
            return query.getResultList();
    }
}
