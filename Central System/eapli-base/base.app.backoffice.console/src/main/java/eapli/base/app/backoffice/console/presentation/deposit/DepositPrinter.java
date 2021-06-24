package eapli.base.app.backoffice.console.presentation.deposit;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.framework.visitor.Visitor;

public class DepositPrinter implements Visitor<Deposit> {

    public void visit(final Deposit visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
