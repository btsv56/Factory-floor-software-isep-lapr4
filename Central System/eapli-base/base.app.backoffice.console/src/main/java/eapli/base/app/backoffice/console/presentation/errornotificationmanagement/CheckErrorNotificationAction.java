package eapli.base.app.backoffice.console.presentation.errornotificationmanagement;

import eapli.framework.actions.Action;

public class CheckErrorNotificationAction implements Action {

    @Override
    public boolean execute() {
        return new CheckErrorNotificationUI().show();
    }
    
}