/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class OperationTask extends TimerTask {

    private final List<ProductionLine> list;
    private final long timeInMilliseconds;
    private final ProductionOrder order;

    public OperationTask(final List<ProductionLine> productionLineList, final long timeIntervalMilli) {
        super();
        this.list = productionLineList;
        this.timeInMilliseconds = timeIntervalMilli;
        this.order = null;
    }

    public OperationTask(final ProductionLine productionLine, final long timeIntervalMilli,
            final ProductionOrder order) {
        super();
        this.list = new LinkedList<>();
        this.list.add(productionLine);
        this.order = order;
        this.timeInMilliseconds = timeIntervalMilli;
    }

    @Override
    public void run() {
        try {
            final Thread[] productionLineThreads = new Thread[list.size()];
            for (int i = 0; i < list.size(); i++) {
                ProcessingUnit.register(list.get(i));
                if (order != null && order.productionLine().sameAs(list.get(i)))
                    productionLineThreads[i] = new ProductionLineThread(list.get(i), order);
                else
                    productionLineThreads[i] = new ProductionLineThread(list.get(i));
                productionLineThreads[i].start();
            }

            Thread.sleep(timeInMilliseconds);

            for (int i = 0; i < list.size(); i++) {
                productionLineThreads[i].interrupt();
                productionLineThreads[i].join();
            }

        } catch (final InterruptedException ex) {
            Logger.getLogger(OperationTask.class.getName()).log(Level.INFO, "The thread was interrupted", ex);
        }
    }

}
