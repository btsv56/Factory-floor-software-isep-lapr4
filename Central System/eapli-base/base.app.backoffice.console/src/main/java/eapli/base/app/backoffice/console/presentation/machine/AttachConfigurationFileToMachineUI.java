/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.machine;

import eapli.base.app.backoffice.console.presentation.productionline.MachinePrinter;
import eapli.base.factoryfloormanagementarea.application.AttachConfigurationFileToMachineController;
import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author bruno
 */
public class AttachConfigurationFileToMachineUI extends AbstractUI {

    final AttachConfigurationFileToMachineController controller = new AttachConfigurationFileToMachineController();

    @Override
    protected boolean doShow() {
        drawFormSeparator();
        System.out.println("\n---" + headline() + "---\n");

        try {
            final Iterable<Machine> machines = controller.allMachines();
            final SelectWidget<Machine> selector = new SelectWidget<>("Select a machine:", machines, new MachinePrinter());
            selector.show();
            final Machine machine = selector.selectedElement();
            final String description = Console.readLine("Configuration description:");
            final String filename = Console.readLine("Insert the filename (name.format):");
            controller.attach(new ConfigurationFile(Description.valueOf(description), new File("./files/configurationfiles/"+filename)), machine);
            System.out.println("Operation Succeeded");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a Machine which already exists in the database.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\nOperation not succedded.\n");
        } catch (IOException e) {
            System.out.println("Error with the given path.");
        } catch (Exception e) {
            System.out.println("Error adding machine.");
        }
        drawFormSeparator();

        return false;
    }

    @Override
    public String headline() {
        return "Attach configuration file to machine";
    }

}
