/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;

import eapli.base.productionresultmanagement.domain.StockMovement;
import eapli.base.productionresultmanagement.domain.FinalProduct;
import eapli.base.productionresultmanagement.domain.ProductionTime;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.messagesmanagement.repositories.MessageRepository;
import eapli.base.messagesmanagement.domain.Message;
import eapli.base.messagesmanagement.domain.*;
import eapli.base.productionordermanagement.domain.ProductionOrder;

import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author bruno
 */
public class MessageProcessorService {

    private final MessageRepository messageRepository;
    private final Machine fromMachine;
    private LocalDateTime startOfActivity;
    private LocalDateTime endOfActivity;
    private final List<ProductionTime> effectiveTimeList, downTimeList;
    private final List<Message> allMessages;

    public MessageProcessorService() {
        this.messageRepository = null;
        this.fromMachine = null;
        this.effectiveTimeList = null;
        this.downTimeList = null;
        this.allMessages = null;
        this.startOfActivity = null;
        this.endOfActivity = null;
    }

    public MessageProcessorService(final Machine fromMachine, final ProductionOrder order) {
        this.messageRepository = PersistenceContext.repositories().messages();
        this.fromMachine = fromMachine;
        processProductionOrder(order);
        this.downTimeList = new ArrayList<>();
        this.effectiveTimeList = new ArrayList<>();
        this.allMessages = new ArrayList<>();
    }

    private void processProductionOrder(final ProductionOrder order) {
        final Message startOfActivityMessage = messageRepository.getStartOfActivityOf(order, fromMachine);
        final Message endOfActivityMessage = messageRepository.getEndOfActivityOf(order, fromMachine,
                startOfActivityMessage.dateHour());
        endOfActivity = endOfActivityMessage.dateHour();
        startOfActivity = startOfActivityMessage.dateHour();
    }

    public List<StockMovement> processStockIn() {
        final ArrayList<StockMovement> stockInList = new ArrayList<>();
        final List<StockMovement> stockInList1 = processOutputDeliveryMessages();
        final List<StockMovement> stockInList2 = processReversalMessages();
        if (stockInList1 != null) {
            stockInList.addAll(stockInList1);
        }
        if (stockInList2 != null) {
            stockInList.addAll(stockInList2);
        }
        return stockInList;
    }

    public List<StockMovement> processStockOut() {
        final ArrayList<StockMovement> stockOutList = new ArrayList<>();
        final List<StockMovement> stockOutList1 = processConsumptionMessages();
        if (stockOutList1 != null) {
            stockOutList.addAll(stockOutList1);
        }
        return stockOutList;
    }

    public void processProductionTimes() {
        processProductionTimeMessages();
    }

    public List<ProductionTime> downTimes() {
        return downTimeList;
    }

    public List<ProductionTime> effectiveTimes() {
        return effectiveTimeList;
    }

    public List<FinalProduct> processFinalProduct() {
        final ArrayList<FinalProduct> finalProductList = new ArrayList<>();
        final List<FinalProduct> finalProductList1 = processOutputMessages();
        if (finalProductList1 != null) {
            finalProductList.addAll(finalProductList1);
        }
        return finalProductList;
    }

    public void updateMessagesProcessingState() {
        if (!allMessages.isEmpty()) {
            allMessages.stream().map((m) -> {
                m.processed();
                return m;
            }).forEachOrdered((m) -> {
                messageRepository.save(m);
            });
        }
    }

    private List<StockMovement> processOutputDeliveryMessages() {
        final Iterable<Message> messages = messageRepository.getOutputDeliveryMessagesBetween(startOfActivity,
                endOfActivity, fromMachine);
        if (messages != null) {
            final ArrayList<StockMovement> stockInList = new ArrayList<>();
            allMessages.addAll((List<Message>) messages);
            for (final Message m : messages) {
                final OutputDeliveryMessage message = (OutputDeliveryMessage) m;
                stockInList
                        .add(new StockMovement(message.quatity(), fromMachine, message.deposit(), message.product()));
            }
            return stockInList;
        } else {
            return null;
        }
    }

    private List<StockMovement> processReversalMessages() {
        final Iterable<Message> messages = messageRepository.getReversalMessagesBetween(startOfActivity, endOfActivity,
                fromMachine);
        if (messages != null) {
            final ArrayList<StockMovement> stockInList = new ArrayList<>();
            allMessages.addAll((List<Message>) messages);
            for (final Message m : messages) {
                final ReversalMessage message = (ReversalMessage) m;
                stockInList.add(new StockMovement(message.quantity(), fromMachine, message.deposit(),
                        message.rawMaterial(), message.product()));
            }
            return stockInList;
        } else {
            return null;
        }
    }

    private List<StockMovement> processConsumptionMessages() {
        final Iterable<Message> messages = messageRepository.getConsumptionMessagesBetween(startOfActivity,
                endOfActivity, fromMachine);
        if (messages != null) {
            final ArrayList<StockMovement> stockOutList = new ArrayList<>();
            allMessages.addAll((List<Message>) messages);
            for (final Message m : messages) {
                final ConsumptionMessage message = (ConsumptionMessage) m;
                stockOutList.add(new StockMovement(message.quantity(), fromMachine, message.deposit(),
                        message.rawMaterial(), message.product()));
            }
            return stockOutList;
        } else {
            return null;
        }
    }

    private List<FinalProduct> processOutputMessages() {
        final Iterable<Message> messages = messageRepository.getOutputMessagesBetween(startOfActivity, endOfActivity,
                fromMachine);
        if (messages != null) {
            final ArrayList<FinalProduct> finalProductList = new ArrayList<>();
            allMessages.addAll((List<Message>) messages);
            for (final Message m : messages) {
                final OutputMessage message = (OutputMessage) m;
                finalProductList.add(new FinalProduct(message.quantity(), message.lot(), message.product()));
            }
            return finalProductList;
        } else {
            return null;
        }
    }

    private void processProductionTimeMessages() {
        final Iterable<Message> messages = messageRepository.getTimeMessagesOrderedByTimeBetween(startOfActivity,
                endOfActivity, fromMachine);
        allMessages.addAll((List<Message>) messages);
        processTimeMessagesAlgorithm(messages, effectiveTimeList, downTimeList);
    }

    private void processTimeMessagesAlgorithm(final Iterable<Message> messages,
            final List<ProductionTime> effectiveTimeList, final List<ProductionTime> downTimeList) {
        int countTimeIntervals = 0, countEffectiveDownTimes = 0;
        Date startDate = null, endDate;

        for (final Message m : messages) {
            if (countTimeIntervals % 2 == 0) {
                startDate = m.date();
            } else {
                endDate = m.date();
                if (countEffectiveDownTimes % 2 == 0) {
                    effectiveTimeList.add(new ProductionTime(fromMachine, startDate, endDate));
                } else {
                    downTimeList.add(new ProductionTime(fromMachine, startDate, endDate));
                }
                countEffectiveDownTimes++;
            }
            countTimeIntervals++;
        }
    }

}
