/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.factoryfloormanagementarea.application.AttachConfigurationFileToMachineController;
import eapli.base.factoryfloormanagementarea.application.SpecifyMachineController;
import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Description;
import eapli.framework.util.Calendars;
import eapli.framework.validations.Invariants;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bruno
 */
public class MachineManagementSmokeTester implements Action {

    private static final Logger LOGGER = LogManager.getLogger(MachineManagementSmokeTester.class);

    final SpecifyMachineController specifyMachineController = new SpecifyMachineController();
    final AttachConfigurationFileToMachineController attachFileToMachie = new AttachConfigurationFileToMachineController();
    final MachineCRUDSmokeTester machineCRUDSmokeTester = new MachineCRUDSmokeTester();
    private final MachineRepository repo = PersistenceContext.repositories().machine();

    @Override
    public boolean execute() {
        testMachineCRUD();
        testSpecifyMachine();
        testAttachConfigurationFileToMachine();

        return true;
    }

    private void testMachineCRUD() {
        machineCRUDSmokeTester.testMachineCRUD();
    }

    private void testSpecifyMachine() {
        final InternalCode id = new InternalCode("10000");

        specifyMachineController.specifyMachine(id, new SerialNumber("1AAAA"), Description.valueOf("smokeTest"), new InstallationDate(Calendars.now()), new Brand("Pops"), new Model("Manus"), new MachineState("active"), new Protocol(44));
        
        final Machine m1 = repo.ofIdentity(id).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found machines of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(m1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains machine of identity");
        
        repo.deleteOfIdentity(m1.identity());
    }
    
    private void testAttachConfigurationFileToMachine(){
        try {
            final InternalCode id = new InternalCode("10000");
            
            final ConfigurationFile configurationFile = new ConfigurationFile(Description.valueOf("Sou fixe"), new File("files/configurationfiles/teste.pdf"));
            LOGGER.info("»»» creates configuration file");
            
            final Machine toMachine = specifyMachineController.specifyMachine(id, new SerialNumber("1AAAA"), Description.valueOf("smokeTest"), new InstallationDate(Calendars.now()), new Brand("Pops"), new Model("Manus"), new MachineState("active"), new Protocol(45));
            LOGGER.info("»»» creates machine");
            
            attachFileToMachie.attach(configurationFile, toMachine);
            LOGGER.info("»»» attach file to machine and saves in repository");
            
            final Machine m1 = repo.ofIdentity(id).orElseThrow(IllegalStateException::new);
            LOGGER.info("»»» found machines of identity");
            
            m1.sameAs(toMachine);
            LOGGER.info("»»» machines match");
            
            repo.deleteOfIdentity(m1.identity());
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(MachineManagementSmokeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
