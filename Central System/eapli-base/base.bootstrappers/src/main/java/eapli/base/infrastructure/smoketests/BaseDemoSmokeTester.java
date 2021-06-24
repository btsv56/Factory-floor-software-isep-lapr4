package eapli.base.infrastructure.smoketests;

import eapli.base.infrastructure.smoketests.backoffice.*;
import eapli.framework.actions.Action;

/**
 * execute simple smoke tests on the application layer. we are assuming that the
 * bootstrappers mainly test the "register" use cases and some of the "finders"
 * to support those "register", so this class will focus mainly on executing the
 * other application methods
 *
 */
@SuppressWarnings("squid:S1126")
public class BaseDemoSmokeTester implements Action {

    @Override
    public boolean execute() {
        System.out.println("\n\n------- SPECIFIC FEATURES -------");
        new RawMaterialManagementSmokeTester().execute();
        new DepositManagementSmokeTester().execute();
        new ProductionLineManagementSmokeTester().execute();
        new MachineManagementSmokeTester().execute();
        new ProductionLineManagementSmokeTester().execute();
        new ProductManagementSmokeTester().execute();
        return true;
    }
}
