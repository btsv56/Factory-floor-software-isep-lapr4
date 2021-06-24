/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.utils.Time;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eapli.framework.application.Controller;

/**
 *
 * @author bruno
 */
public class MessageProcessingServiceController implements Controller {
    
    private final ListProductionLineService listService = new ListProductionLineService();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrder();
    
    public Iterable<ProductionLine> listProductionLines(){
        return listService.allProductionLines();
    }

    public void changeMessageProcessingStateOf(final ProductionLine productionLine) {
        ProcessingUnit.changeProductionLineStatus(productionLine);
	}

	public Iterable<ProductionOrder> listProductionOrders() {
		return productionOrderRepository.findAll();
	}

	public void reprocessMessagesAtGivenTime(final Time startTime, final int interval, final ProductionOrder order) {
        final ProductionLine line = order.productionLine();
        ProcessingUnit.scheduleMessageProcessing(startTime, interval, line, order);
	}

	public Map<ProductionLine, Date> listActiveProductionLinesWithProcessedTimes() {
		return ProcessingUnit.registryOfActiveProductionLines();
	}

	public Map<ProductionLine, Date> listDeactiveProductionLinesWithProcessedTimes() {
		return ProcessingUnit.registryOfDeactiveProductionLines(listProductionLines());
	}

	public List<ProductionLine> listActiveProductionLines() {
		return ProcessingUnit.activeProductionLines();
	}

	public boolean automaticProcessingIsActive() {
		return ProcessingUnit.automaticProcessingIsActive();
	}

	public void configureNewAutomaticProcess(final Time startTime, final int interval) {
		ProcessingUnit.configureAutomatic(startTime, interval);
		ProcessingUnit.activateAutomaticProcessing();
	}
}
