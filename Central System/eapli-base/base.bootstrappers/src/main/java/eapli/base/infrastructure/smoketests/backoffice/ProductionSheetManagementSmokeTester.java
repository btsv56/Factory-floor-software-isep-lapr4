package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class ProductionSheetManagementSmokeTester implements Action {

    final private ProductionSheetCRUDSmokeTester productionSheetCRUDSmokeTester = new ProductionSheetCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductionSheetCRUD();
        return true;
    }

    private void testProductionSheetCRUD(){
        productionSheetCRUDSmokeTester.testProductCRUD();
    }
}
