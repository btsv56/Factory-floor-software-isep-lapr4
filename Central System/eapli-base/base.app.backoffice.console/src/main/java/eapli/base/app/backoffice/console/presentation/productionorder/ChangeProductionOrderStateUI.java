package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.productionordermanagement.application.ChangeProductionOrderStateController;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

public class ChangeProductionOrderStateUI extends AbstractUI {

    private ChangeProductionOrderStateController controller = new ChangeProductionOrderStateController();

    protected Controller controller() {
        return this.controller;
    }

    @Override
    protected boolean doShow() {
        //System.out.println("Not implemented yet, sorry.");
        final Iterable<ProductionOrder> pendOrSus = controller.getPendingOrSuspended();
        if (pendOrSus.iterator().hasNext()) {
            final SelectWidget<ProductionOrder> selector = new SelectWidget<>("Production orders (these are either pending or suspended):", pendOrSus, new ProductionOrderPrinter());
            selector.show();
            final ProductionOrder prodOrder = selector.selectedElement();
            if (prodOrder != null) {
                if (!controller.changeState(prodOrder)) {
                    System.out.println("Production order was not updated.");
                    return false;
                } else {
                    System.out.println("Production order state changed.");
                }
            }
        } else { System.out.println("There are no production orders available."); return false; }
        return false;
    }

    @Override
    public String headline() {
        return "Change a Production Order's state";
    }
}
