package eapli.base.app.backoffice.console.presentation.productionline;

import eapli.framework.actions.Action;

public class ListProductionLineAction implements Action {

    @Override
    public boolean execute() {
        return new ListProductionLineUI().show();
    }
}
