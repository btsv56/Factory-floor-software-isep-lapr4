package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis.SeeMessageProcessingStateUI;
import eapli.framework.actions.Action;

public class SeeMessageProcessingStateAction implements Action {

    @Override
    public boolean execute() {
        return new SeeMessageProcessingStateUI().show();
    }
    
}