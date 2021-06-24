/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.domain.ProductionResultBuilder;
import eapli.base.productionresultmanagement.repositories.ProductionResultRepository;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author bruno
 */
public class ProductionLineThread extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(ProductionLineThread.class);
    private final ProductionLine productionLine;
    private final ProductionResultRepository producitonResultRepository;
    private final ProductionOrderRepository productionOrderRepository;
    private final ProductionResultBuilder productionResultBuilder;
    private final List<ProductionOrder> productionOrders;

    public ProductionLineThread(final ProductionLine productionLine) {
        Preconditions.nonNull(productionLine);
        this.productionLine = productionLine;
        this.productionResultBuilder = new ProductionResultBuilder();
        this.productionOrderRepository = PersistenceContext.repositories().productionOrder();
        this.producitonResultRepository = PersistenceContext.repositories().productionResult();
        this.productionOrders = (List<ProductionOrder>)productionOrderRepository.getUnprocessedProductionOrdersBy(productionLine);
    }

    public ProductionLineThread(final ProductionLine productionLine, final ProductionOrder order){ 
        Preconditions.noneNull(productionLine, order);
        this.productionLine = productionLine;
        this.productionResultBuilder = new ProductionResultBuilder();
        this.productionOrderRepository = PersistenceContext.repositories().productionOrder();
        this.producitonResultRepository = PersistenceContext.repositories().productionResult();
        this.productionOrders = new LinkedList<>();
        this.productionOrders.add(order);
    }

    @Override
    public void run() {
        ProductionResult productionResult;
        MessageProcessorService mps = new MessageProcessorService();

        for (final ProductionOrder order : productionOrders) {
            productionResultBuilder.newProductionResult();
            productionResultBuilder.addProductionOrder(order);
            for (final Machine machine : productionLine.machines()) {
                if (Thread.interrupted())
                    return;
                try {
                    mps = new MessageProcessorService(machine, order);
                    mps.processProductionTimes();
                    productionResultBuilder.addDownTime(mps.downTimes())
                            .addEffectiveTime(mps.effectiveTimes())
                            .addFinalProduct(mps.processFinalProduct())
                            .addStockIn(mps.processStockIn())
                            .addStockOut(mps.processStockOut());
                } catch (final NoResultException e) {
                    LOGGER.trace("Messages not found for " + machine + ": ", e);
                }
            }
            productionResult = productionResultBuilder.build();
            producitonResultRepository.save(productionResult);
            mps.updateMessagesProcessingState();
            order.processed();
            productionOrderRepository.save(order);
        }
    }
}
