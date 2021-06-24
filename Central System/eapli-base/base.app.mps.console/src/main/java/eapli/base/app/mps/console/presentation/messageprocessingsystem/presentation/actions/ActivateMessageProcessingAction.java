package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis.ActivateMessageProcessingUI;
import eapli.framework.actions.Action;

public class ActivateMessageProcessingAction implements Action {

    @Override
    public boolean execute() {
        return new ActivateMessageProcessingUI().show();
    }
    
}