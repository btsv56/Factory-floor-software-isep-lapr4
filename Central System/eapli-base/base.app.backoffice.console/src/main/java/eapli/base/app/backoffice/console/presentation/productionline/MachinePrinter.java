package eapli.base.app.backoffice.console.presentation.productionline;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.visitor.Visitor;

public class MachinePrinter implements Visitor<Machine> {

    @Override
    public void visit(final Machine visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
