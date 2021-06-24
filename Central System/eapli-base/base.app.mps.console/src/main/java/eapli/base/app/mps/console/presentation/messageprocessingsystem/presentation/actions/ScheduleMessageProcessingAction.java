package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis.ScheduleMessageProcessingUI;
import eapli.framework.actions.Action;

public class ScheduleMessageProcessingAction implements Action {

    @Override
    public boolean execute() {
        return new ScheduleMessageProcessingUI().show();
    }
    
}