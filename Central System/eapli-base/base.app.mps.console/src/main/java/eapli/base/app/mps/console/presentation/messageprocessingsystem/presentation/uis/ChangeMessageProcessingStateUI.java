package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis;

import java.util.List;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.printers.ProductionLinePrinter;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionresultmanagement.application.MessageProcessingServiceController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class ChangeMessageProcessingStateUI extends AbstractUI {

    private final MessageProcessingServiceController controller = new MessageProcessingServiceController();

    @Override
    protected boolean doShow() {
        List<ProductionLine> productionLines;

        System.out.println("Do you want to activate or deactive?");
        System.out.println("1. Activate");
        System.out.println("2. Deactivate");
        final int option = Console.readOption(1, 2, 0);
        if (option != 0) {
            if (option == 1) {
                productionLines = (List<ProductionLine>) controller.listProductionLines();
                productionLines.removeAll((List<ProductionLine>) controller.listActiveProductionLines());
            } else {
                productionLines = (List<ProductionLine>) controller.listActiveProductionLines();
            }
            final SelectWidget<ProductionLine> selector = new SelectWidget<>("Select the Production Line:",
                    productionLines, new ProductionLinePrinter());
            selector.show();
            final ProductionLine productionLine = selector.selectedElement();
            try {
                controller.changeMessageProcessingStateOf(productionLine);
                if (!controller.automaticProcessingIsActive()){
                    System.out.println("WARNING: You need to activate the automatic processing.");
                }
            } catch (final Exception e) {
                System.out.println("Couldn't change processing state.");
                return true;
            }
        }
        return false;
    }

    @Override
    public String headline() {
        return "Change Production Line State";
    }
    
}