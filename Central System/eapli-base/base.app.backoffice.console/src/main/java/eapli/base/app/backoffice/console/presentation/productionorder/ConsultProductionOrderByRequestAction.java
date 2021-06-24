package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.framework.actions.Action;

public class ConsultProductionOrderByRequestAction implements Action {

    @Override
    public boolean execute() {
        return new ConsultProductionOrderByRequestUI().show();
    }
}
