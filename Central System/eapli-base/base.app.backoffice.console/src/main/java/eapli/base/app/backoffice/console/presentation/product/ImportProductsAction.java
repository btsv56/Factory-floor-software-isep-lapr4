package eapli.base.app.backoffice.console.presentation.product;

import eapli.framework.actions.Action;

public class ImportProductsAction implements Action {
    @Override
    public boolean execute() {
        return new ImportProductsUI().show();
    }
}
