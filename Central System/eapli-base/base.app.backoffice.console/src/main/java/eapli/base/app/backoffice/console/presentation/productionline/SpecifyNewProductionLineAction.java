package eapli.base.app.backoffice.console.presentation.productionline;

import eapli.framework.actions.Action;

public class SpecifyNewProductionLineAction implements Action {

    @Override
    public boolean execute() {
        return new SpecifyNewProductionLineUI().show();
    }
}
