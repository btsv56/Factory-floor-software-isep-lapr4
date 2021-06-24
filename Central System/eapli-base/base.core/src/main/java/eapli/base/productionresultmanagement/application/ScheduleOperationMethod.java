/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.utils.Time;

/**
 *
 * @author bruno
 */
class ScheduleOperationMethod extends OperationMethod implements OperationMethodStrategy {

    private final ProductionLine line;
    private final Time startTime;
    private final long interval;
    private final ProductionOrder order;

    ScheduleOperationMethod(final ProductionLine line, final Time startTime, final int interval, final ProductionOrder order) {
        this.line = line;
        this.startTime = startTime == null ? Time.now() : startTime;
        this.interval = interval == 0 ? Time.minutesToMilli(15) : Time.minutesToMilli(interval);
        this.order = order;
    }

    @Override
    public void operate() {
        final OperationTask task = new OperationTask(line, interval, order);
        final long delay = calculateDelay(startTime);
        final OperationTimer timer = new OperationTimer();
        timer.schedule(task, delay);
    }    
    
}
