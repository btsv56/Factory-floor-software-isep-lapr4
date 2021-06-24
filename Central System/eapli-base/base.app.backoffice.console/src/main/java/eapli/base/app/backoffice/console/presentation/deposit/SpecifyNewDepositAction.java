package eapli.base.app.backoffice.console.presentation.deposit;

import eapli.framework.actions.Action;

public class SpecifyNewDepositAction implements Action {

    @Override
    public boolean execute() {
        return new SpecifyNewDepositUI().show();
    }
}
