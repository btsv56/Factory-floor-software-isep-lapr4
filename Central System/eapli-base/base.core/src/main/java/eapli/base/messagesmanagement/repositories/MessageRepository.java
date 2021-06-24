/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.repositories;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.repositories.DomainRepository;

import java.time.LocalDateTime;

/**
 *
 * @author Utilizador
 */
public interface MessageRepository extends DomainRepository<Long,Message>{

    public Message getStartOfActivityOf(ProductionOrder order, Machine machine);

    public Message getEndOfActivityOf(ProductionOrder order, Machine machine, LocalDateTime time);

    public Iterable<Message> getOutputDeliveryMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine);

    public Iterable<Message> getReversalMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine);

    public Iterable<Message> getConsumptionMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine);

    public Iterable<Message> getOutputMessagesBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine);

    public Iterable<Message> getTimeMessagesOrderedByTimeBetween(LocalDateTime startOfActivity, LocalDateTime endOfActivity, Machine fromMachine);
    
}
