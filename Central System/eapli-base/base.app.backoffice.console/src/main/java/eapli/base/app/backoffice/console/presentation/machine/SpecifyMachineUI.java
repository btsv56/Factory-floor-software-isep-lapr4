/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.machine;

import eapli.base.factoryfloormanagementarea.application.SpecifyMachineController;
import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.Calendar;

/**
 *
 * @author bruno
 */
public class SpecifyMachineUI extends AbstractUI {
    
    private final SpecifyMachineController theController = new SpecifyMachineController();

    @Override
    protected boolean doShow() {
        
        drawFormSeparator();
        System.out.println("\n---" + headline() + "---\n");
        
        final String internalCode = Console.readLine("\nInternal Code: ");
        final String serialNumber = Console.readLine("\nSerial Number: ");
        final String description = Console.readLine("\nDescription: ");
        final Calendar dateOfInstalation = Console.readCalendar("\nDate of Instalation (dd-mm-yyyy):");
        final String brand = Console.readLine("\nBrand:");
        final String model = Console.readLine("\nModel:");
        final String machineState = Console.readLine("\nState: ");
        final int protocol = Console.readInteger("\nProtocol: ");
        try{
            if (theController.specifyMachine(new InternalCode(internalCode), new SerialNumber(serialNumber), Description.valueOf(description),
                    new InstallationDate(dateOfInstalation), new Brand(brand), new Model(model), new MachineState(machineState), new Protocol(protocol))==null)
                System.out.println("Serial number and/or protocol must be unique");
            else System.out.println("Operation Succeeded");
        }catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("You tried to enter a Machine which already exists in the database.\n");
        }catch (IllegalArgumentException e){
            System.out.println("\n" + e.getMessage() + "\nOperation not succedded.\n");
        }catch (Exception e) {
            System.out.println("Error adding machine.");
        }
        drawFormSeparator();
        
        return false;
    }

    @Override
    public String headline() {
        return "Specify a new Machine";
    }
    
    
}
