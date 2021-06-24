package eapli.base.app.backoffice.console.presentation.message;

import eapli.framework.actions.Action;

public class CheckArchivedErrorNotificationsAction implements Action {
    @Override
    public boolean execute() {
        return new CheckArchivedErrorNotificationsUI().show();
    }
}
