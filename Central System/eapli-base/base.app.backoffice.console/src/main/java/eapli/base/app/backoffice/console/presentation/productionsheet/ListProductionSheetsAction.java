package eapli.base.app.backoffice.console.presentation.productionsheet;

import eapli.framework.actions.Action;

public class ListProductionSheetsAction implements Action {

    @Override
    public boolean execute() {
        return new ListProductionSheetsUI().show();
    }
}
