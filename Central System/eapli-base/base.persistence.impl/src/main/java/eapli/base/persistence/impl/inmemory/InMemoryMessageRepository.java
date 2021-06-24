/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.messagesmanagement.application.MessageComparator;
import eapli.base.messagesmanagement.domain.EndOfActivityMessage;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.messagesmanagement.repositories.MessageRepository;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import eapli.base.messagesmanagement.domain.MessageType;
import eapli.base.messagesmanagement.domain.StartOfActivityMessage;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class InMemoryMessageRepository extends InMemoryDomainRepository<Long,Message> implements MessageRepository {
    
    static{
        InMemoryInitializer.init();
    }

    @Override
    public Message getStartOfActivityOf(ProductionOrder order, Machine machine) {
        Iterable<Message> m = match(e -> e.machine().equals(machine) && e.type().toString().equals(MessageType.START_OF_ACTIVITY));
        for (Message message : m) {
            StartOfActivityMessage startMessage = (StartOfActivityMessage)message;
            if (startMessage.order().equals(order))
                return startMessage;
        }
        return null;
    }

    @Override
    public Message getEndOfActivityOf(ProductionOrder order, Machine machine, LocalDateTime time) {
        Iterable<Message> m = match(e -> e.machine().equals(machine) && e.type().toString().equals(MessageType.END_OF_ACTIVITY));
        for (Message message : m) {
            EndOfActivityMessage startMessage = (EndOfActivityMessage)message;
            if (startMessage.order().equals(order))
                return startMessage;
        }
        return null;
    }

    @Override
    public Iterable<Message> getOutputDeliveryMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine) {
        Iterable<Message> m = match(e -> e.machine().equals(fromMachine) && e.type().toString().equals(MessageType.OUTPUT_DELIVERY));
        List<Message> list = new LinkedList<>();
        for (Message message : m) {
            if (message.dateHour().isBefore(endOfActivity) &&
                    message.dateHour().isAfter(startOfActivity))
                list.add(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> getReversalMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine) {
        Iterable<Message> m = match(e -> e.machine().equals(fromMachine) && e.type().toString().equals(MessageType.REVERSAL));
        List<Message> list = new LinkedList<>();
        for (Message message : m) {
            if (message.dateHour().isBefore(endOfActivity) &&
                    message.dateHour().isAfter(startOfActivity))
                list.add(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> getConsumptionMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine) {
        Iterable<Message> m = match(e -> e.machine().equals(fromMachine) && e.type().toString().equals(MessageType.CONSUMPTION));
        List<Message> list = new LinkedList<>();
        for (Message message : m) {
            if (message.dateHour().isBefore(endOfActivity) &&
                    message.dateHour().isAfter(startOfActivity))
                list.add(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> getOutputMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine) {
        Iterable<Message> m = match(e -> e.machine().equals(fromMachine) && e.type().toString().equals(MessageType.OUTPUT_DELIVERY));
        List<Message> list = new LinkedList<>();
        for (Message message : m) {
            if (message.dateHour().isBefore(endOfActivity) &&
                    message.dateHour().isAfter(startOfActivity))
                list.add(message);
        }
        return list;
    }

    @Override
    public Iterable<Message> getTimeMessagesOrderedByTimeBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine) {
        Iterable<Message> m = match(e -> e.machine().equals(fromMachine) && e.type().toString().equals(MessageType.OUTPUT_DELIVERY));
        List<Message> list = new LinkedList<>();
        for (Message message : m) {
            if (message.dateHour().isBefore(endOfActivity) &&
                    message.dateHour().isAfter(startOfActivity))
                list.add(message);
        }
        Collections.sort(list, new MessageComparator());
        return list;
    }
    
}
