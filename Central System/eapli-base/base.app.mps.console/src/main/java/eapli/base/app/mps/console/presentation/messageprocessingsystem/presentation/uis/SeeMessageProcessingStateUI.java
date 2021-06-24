package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis;

import java.util.Date;
import java.util.Map;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionresultmanagement.application.MessageProcessingServiceController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class SeeMessageProcessingStateUI extends AbstractUI {

    private final MessageProcessingServiceController controller = new MessageProcessingServiceController();
    private final String tab = "    ";

    @Override
    protected boolean doShow() {


        final Map<ProductionLine, Date> activeProductionLines = controller
                .listActiveProductionLinesWithProcessedTimes();
        System.out.println("Active:");
        for (final ProductionLine line : activeProductionLines.keySet()) {
            final Date date = activeProductionLines.get(line);
            System.out.println(tab + "-> " + line.toStringSimple() + " : " + (date == null ? "None." : date));
        }

        final Map<ProductionLine, Date> deactiveProductionLines = controller
                .listDeactiveProductionLinesWithProcessedTimes();
        System.out.println("Deactive:");
        for (final ProductionLine line : deactiveProductionLines.keySet()) {
            final Date date = deactiveProductionLines.get(line);
            System.out.println(tab + "-> " + line.toStringSimple() + " : " + (date == null ? "None." : date));
        }

        System.out.println("");

        Console.readLine("Press Enter to continue.");
        return false;
    }

    @Override
    public String headline() {
        return "State of Production Lines and the last time it was processed";
    }
    
}