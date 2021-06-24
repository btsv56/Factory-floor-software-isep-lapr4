package eapli.base.productionresultmanagement.application;

import java.util.List;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.utils.Time;

class AutomaticOperationMethod extends OperationMethod implements OperationMethodStrategy {

    private final List<ProductionLine> list;
    private final Time startTime;
    private final long interval;
    private final OperationTimer timer;

    AutomaticOperationMethod(Time startTime, int interval){
        this.list = ProcessingUnit.activeProductionLines();
        this.startTime = startTime == null ? Time.now() : startTime;
        this.interval = interval == 0 ? Time.minutesToMilli(15) : Time.minutesToMilli(interval);
        this.timer = new OperationTimer();
    }

    AutomaticOperationMethod(){
        this.list = ProcessingUnit.activeProductionLines();
        this.startTime = Time.now();
        this.interval = Time.minutesToMilli(15);
        this.timer = new OperationTimer();
    }

    @Override
    public void operate() {
        final OperationTask task = new OperationTask(list, interval);
        final long delay = calculateDelay(startTime);
        final long period = Time.hoursToMilli((int)interval);
        timer.scheduleAtFixedRate(task, delay, period);
    }

    void cease(){
        timer.cancel();
    }

}
