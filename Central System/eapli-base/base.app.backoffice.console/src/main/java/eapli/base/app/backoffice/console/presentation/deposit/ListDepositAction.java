package eapli.base.app.backoffice.console.presentation.deposit;

import eapli.framework.actions.Action;

public class ListDepositAction implements Action {

    @Override
    public boolean execute() {
        return new ListDepositUI().show();
    }
}
