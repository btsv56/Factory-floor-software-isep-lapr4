package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis.ChangeMessageProcessingStateUI;
import eapli.framework.actions.Action;

public class ChangeMessageProcessingStateAction implements Action {

    @Override
    public boolean execute() {
        return new ChangeMessageProcessingStateUI().show();
    }
    
}