/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import static eapli.base.infrastructure.bootstrapers.TestDataConstants.*;
import eapli.base.factoryfloormanagementarea.application.SpecifyMachineController;
import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.util.Calendars;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bruno
 */
public class MachineBootstrapper implements Action {
    
    private static final Logger LOGGER = LogManager.getLogger(MachineBootstrapper.class);

    @Override
    public boolean execute() {
        register(MACHINE_1, new SerialNumber("1A"), Description.valueOf("machine 1"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1000));
        register(MACHINE_2, new SerialNumber("1B"), Description.valueOf("machine 2"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1001));
        register(MACHINE_3, new SerialNumber("1C"), Description.valueOf("machine 3"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1002));
        register(MACHINE_4, new SerialNumber("1D"), Description.valueOf("machine 4"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1003));
        register(MACHINE_5, new SerialNumber("1E"), Description.valueOf("machine 5"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1004));
        register(MACHINE_6, new SerialNumber("1F"), Description.valueOf("machine 6"), new InstallationDate(Calendars.of(2007,3,5)), new Brand("Pops"), new Model("Manu"), new MachineState("standby"), new Protocol(1005));
    
        return true;
    }
    
    private void register(final InternalCode internalCode, final SerialNumber serialNumber, final Description description,
            final InstallationDate dateOfInstallation, final Brand brand, final Model model,
            final MachineState machineState, final Protocol protocol) {
        final SpecifyMachineController controller = new SpecifyMachineController();
        try {
            controller.specifyMachine(internalCode, serialNumber, description, dateOfInstallation, brand, model, machineState, protocol);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", description);
            LOGGER.trace("Assuming existing record", e);
        }
    }
    
}
