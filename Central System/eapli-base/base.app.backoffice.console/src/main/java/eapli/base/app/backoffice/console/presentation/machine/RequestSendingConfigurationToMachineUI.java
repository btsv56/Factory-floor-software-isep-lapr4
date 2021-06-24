/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.machine;

import eapli.base.factoryfloormanagementarea.application.RequestSendingConfigurationToMachineController;
import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public class RequestSendingConfigurationToMachineUI extends AbstractUI {

    private final RequestSendingConfigurationToMachineController theController = new RequestSendingConfigurationToMachineController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        final Iterable<Machine> machines = this.theController.getAllMachines();

        final SelectWidget<Machine> selector = new SelectWidget<>("Machines:", machines,
                new MachinePrinter());
        System.out.println("Please make sure SCM is on before proceeding.");
        Console.readLine("(Press Enter to continue)");
        selector.show();
        final Machine theMachine=selector.selectedElement();
        if(theMachine==null) return false;
        final Iterable<ConfigurationFile> configFiles = theMachine.configList();
        if(!configFiles.iterator().hasNext()){
            System.out.println("This machine has no configuration files");
            return false;
        }
        final SelectWidget<ConfigurationFile> selectorc = new SelectWidget<>("Configuration files:", configFiles,
                new ConfigFilesPrinter());
        selectorc.show();
        final ConfigurationFile theConfigFile= selectorc.selectedElement();
        if(theConfigFile==null) return false;
        try {
            if (theController.sendConfigurationFile(theMachine, theConfigFile)) {
                System.out.println("Connection to SCM successful. Please check SCM for any errors.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String headline() {
        return "Request sending a configuration to machine";
    }

}
