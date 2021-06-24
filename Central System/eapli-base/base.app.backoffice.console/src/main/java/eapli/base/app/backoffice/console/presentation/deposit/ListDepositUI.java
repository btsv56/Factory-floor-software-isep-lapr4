package eapli.base.app.backoffice.console.presentation.deposit;

import eapli.base.depositmanagement.application.ListDepositController;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListDepositUI extends AbstractListUI<Deposit> {

    final ListDepositController theController = new ListDepositController();

    @Override
    protected Iterable<Deposit> elements() {
        return this.theController.allDeposits();
    }

    @Override
    protected Visitor<Deposit> elementPrinter() {
        return new DepositPrinter();
    }

    @Override
    protected String elementName() {
        return "Deposit";
    }

    @Override
    protected String listHeader() {
        return "Deposits";
    }
}
