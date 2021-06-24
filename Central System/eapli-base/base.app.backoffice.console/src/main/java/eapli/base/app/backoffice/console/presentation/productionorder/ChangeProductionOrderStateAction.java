package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.framework.actions.Action;

public class ChangeProductionOrderStateAction implements Action {

    @Override
    public boolean execute() {
        return new ChangeProductionOrderStateUI().show();
    }
}
