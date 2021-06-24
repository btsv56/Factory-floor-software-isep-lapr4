package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

public class DepositManagementSmokeTester implements Action {

    final DepositCRUDSmokeTester depositSmokeTester = new DepositCRUDSmokeTester();

    @Override
    public boolean execute() {
        testDepositCRUD();
        return true;
    }

    private void testDepositCRUD() {
        depositSmokeTester.testDeposit();
    }

}
