package eapli.base.productionresultmanagement.application;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.utils.Time;

public class ProcessingUnit {

    private static ProcessingUnit instance;
    private final List<ProductionLine> activeProductionLines;
    private final Map<ProductionLine, Date> registry;
    private AutomaticOperationMethod automaticMethod;
    private boolean automaticProcessingIsActive;

    private static ProcessingUnit instance() {
        if (instance == null) {
            instance = new ProcessingUnit();
        }
        return instance;
    }

    public static void configureAutomatic(Time startTime, int interval) {
        ProcessingUnit instance = instance();
        if (instance.automaticProcessingIsActive == true) {
            instance.automaticMethod.cease();
            instance.automaticProcessingIsActive = false;
        }
        instance.automaticMethod = new AutomaticOperationMethod(startTime, interval);
    }

    private ProcessingUnit() {
        activeProductionLines = new LinkedList<>();
        registry = new HashMap<>();
        automaticProcessingIsActive = false;
    }

    public static void activate(final ProductionLine pl) {
        instance().activeProductionLines.add(pl);
    }

    public static void activate(final List<ProductionLine> pl) {
        instance().activeProductionLines.addAll(pl);
    }

    public static void activateAutomaticProcessing() {
        ProcessingUnit instance = instance();
        instance.automaticProcessingIsActive = true;
        instance.automaticMethod.operate();
    }

    public static List<ProductionLine> activeProductionLines() {
        return new LinkedList<>(instance().activeProductionLines);
    }

    public static void deactivate(final ProductionLine pl) {
        instance().activeProductionLines.remove(pl);
    }

    public static void changeProductionLineStatus(final ProductionLine productionLine) {
        if (instance().activeProductionLines.contains(productionLine)) {
            deactivate(productionLine);
        }
        activate(productionLine);
    }

    public static void scheduleMessageProcessing(final Time startTime, final int interval, final ProductionLine line, final ProductionOrder order) {
        final ScheduleOperationMethod method = new ScheduleOperationMethod(line, startTime, interval, order);
        method.operate();
    }

    static void register(ProductionLine productionLine) {
        instance().registry.put(productionLine, new Date());
    }

    public static Map<ProductionLine, Date> registryOfActiveProductionLines() {
        Map<ProductionLine, Date> map = new HashMap<>();
        for (ProductionLine productionLine : instance().activeProductionLines) {
            if (instance().registry.containsKey(productionLine))
                map.put(productionLine, instance().registry.get(productionLine));
            else
                map.put(productionLine, null);
        }
        return map;
    }

    public static Map<ProductionLine, Date> registryOfDeactiveProductionLines(
            Iterable<ProductionLine> allProdcutionLines) {
        Map<ProductionLine, Date> map = new HashMap<>();
        ProcessingUnit instance = instance();
        for (ProductionLine productionLine : allProdcutionLines) {
            if (!activeProductionLines().contains(productionLine))
                if (!instance.registry.containsKey(productionLine))
                    map.put(productionLine, null);
                else
                    map.put(productionLine, instance.registry.get(productionLine));
        }
        return map;
    }

    public static boolean automaticProcessingIsActive() {
        return instance().automaticProcessingIsActive;
    }

}