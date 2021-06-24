package eapli.base.infrastructure.bootstrapers.demo;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.application.SpecifyNewProductionLineController;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

public class ProductionLineBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(DepositBootstrapper.class);

    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();

    private Machine getMachine(final InternalCode name) {
        return machineRepository.ofIdentity(name).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {

        final Machine mach1 = getMachine(TestDataConstants.MACHINE_1);
        final Machine mach2 = getMachine(TestDataConstants.MACHINE_2);
        final Machine mach3 = getMachine(TestDataConstants.MACHINE_3);
        final Machine mach4 = getMachine(TestDataConstants.MACHINE_4);

        ArrayList<Machine> machArray = new ArrayList<>();

        machArray.add(mach1);
        machArray.add(mach2);
        register(TestDataConstants.PLINE_1, "Production Line 1", machArray);
        machArray.clear();

        machArray.add(mach3);
        machArray.add(mach4);
        register(TestDataConstants.PLINE_2, "Production Line 2", machArray);
        machArray.clear();

        register(TestDataConstants.PLINE_3, "Production Line 3", null);
        register(TestDataConstants.PLINE_4, "Production Line 4", null);
        register(TestDataConstants.PLINE_5, "Production Line 5", null);

        return true;
    }

    /**
     *
     */
    private void register(final String code, final String desc, ArrayList<Machine> machines) {
        final SpecifyNewProductionLineController controller = new SpecifyNewProductionLineController();
        try {
            controller.specifyNewProductionLine(Designation.valueOf(code), desc);
            if (machines!=null) {
                for (Machine mach : machines) {
                    controller.addMachine(mach);
                }
            }
            controller.save();
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", desc);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
