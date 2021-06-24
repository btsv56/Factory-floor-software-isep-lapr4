package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.productionordermanagement.application.ConsultProductionOrderByRequestController;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class ConsultProductionOrderByRequestUI extends AbstractUI {

    private ConsultProductionOrderByRequestController controller = new ConsultProductionOrderByRequestController();

    protected Controller controller() {
        return this.controller;
    }

    @Override
    protected boolean doShow() {
        String choice= Console.readLine("Request ID:");
        final Iterable<ProductionOrder> prodOrds = controller.byRequest(choice);
        if (prodOrds.iterator().hasNext()) {
            final SelectWidget<ProductionOrder> selector = new SelectWidget<>("Production orders of request "+choice+":", prodOrds, new ProductionOrderPrinter());
            selector.show();
            final ProductionOrder prodOrder = selector.selectedElement();
            if (prodOrder != null) {
                System.out.println(prodOrder.toStringComplete());
            }
        } else { System.out.println("No production orders belonging to that request were found."); return false; }
        return false;
    }

    @Override
    public String headline() {
        return "Consult a Production Order by Request";
    }
}
