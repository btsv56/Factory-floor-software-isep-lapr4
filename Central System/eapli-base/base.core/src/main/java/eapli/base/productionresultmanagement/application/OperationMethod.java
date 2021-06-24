package eapli.base.productionresultmanagement.application;

import eapli.base.utils.Time;

public abstract class OperationMethod {

    long calculateDelay(Time startTime) {
        Time now = Time.now();
        if (now.isAfter(startTime)){
            startTime.addHours(24);
            return Time.timeToMilli(startTime) - Time.timeToMilli(now);
        }
        return Time.timeToMilli(startTime) - Time.timeToMilli(now);
    }
    
}