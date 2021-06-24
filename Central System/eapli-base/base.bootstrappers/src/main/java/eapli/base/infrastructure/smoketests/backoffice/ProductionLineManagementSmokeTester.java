package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class ProductionLineManagementSmokeTester implements Action {

    final ProductionLineCRUDSmokeTester pLSmokeTester = new ProductionLineCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductionLineCRUD();
        return true;
    }

    private void testProductionLineCRUD() {
        pLSmokeTester.testProductionLines();
    }
}
