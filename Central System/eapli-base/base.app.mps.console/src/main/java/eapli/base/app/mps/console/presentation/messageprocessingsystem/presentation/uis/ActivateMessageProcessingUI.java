package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis;

import eapli.base.app.mps.console.utils.ConsoleUtility;
import eapli.base.productionresultmanagement.application.MessageProcessingServiceController;
import eapli.base.utils.Time;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ActivateMessageProcessingUI extends AbstractUI{

    private final MessageProcessingServiceController controller = new MessageProcessingServiceController();

    @Override
    protected boolean doShow() {
        if (controller.automaticProcessingIsActive()){
            final boolean wantsToChange = Console.readBoolean(
                    "Automatic processing is already active. Do you want to change the occurence period? (y/n)");
            if (wantsToChange){
                return changeOrActiveAutomaticProcessing();
            }
        } else {
            return changeOrActiveAutomaticProcessing();
        }   
        return true;     
    }

    private boolean changeOrActiveAutomaticProcessing(){
        final Time startTime = ConsoleUtility.readHourOfDay("When do you want it to start? [hh:mm] (empty for now) ");
        final int interval = ConsoleUtility.readMinutes("How often? (in minutes, leave empty for 15 mins) ");

        try {
            controller.configureNewAutomaticProcess(startTime, interval);
        } catch (final Exception e) {
            System.out.println("Could not set up a new process.");
            return true;
        }
        
        return false;
    }

    

    @Override
    public String headline() {
        return "Activate or change automatic message processing";
    }

}
