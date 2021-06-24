/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.model.general.Description;
import eapli.framework.util.Calendars;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bruno
 */
public class MachineCRUDSmokeTester {
    private static final Logger LOGGER = LogManager.getLogger(MachineCRUDSmokeTester.class);
    private final MachineRepository repo = PersistenceContext.repositories().machine();
    
    public void testMachineCRUD() {
        // save
        repo.save(new Machine(new InternalCode("one"), new SerialNumber("one"), Description.valueOf("smokeTest"), new InstallationDate(Calendars.now()), new Brand("Pops"), new Model("Manus"), new MachineState("active"), new Protocol(32)));
        repo.save(new Machine(new InternalCode("two"), new SerialNumber("two"), Description.valueOf("smokeTest"), new InstallationDate(Calendars.now()), new Brand("Pops"), new Model("Manus"), new MachineState("active"), new Protocol(32)));
        LOGGER.info("»»» created machines");

        // findAll
        final Iterable<Machine> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all machines");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # machines = {}", n);

        // ofIdentity
        final Machine m1 = repo.ofIdentity(new InternalCode("one")).orElseThrow(IllegalStateException::new);
        final Machine m2 = repo.ofIdentity(new InternalCode("two")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found machines of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(m1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains machine of identity");

        // contains
        final boolean has = repo.contains(m1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains machine");

        // delete
        repo.delete(m1);
        LOGGER.info("»»» delete machines");

        // deleteOfIdentity
        repo.deleteOfIdentity(m2.identity());
        LOGGER.info("»»» delete machines of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # machines = {}", n2);
    }
}
