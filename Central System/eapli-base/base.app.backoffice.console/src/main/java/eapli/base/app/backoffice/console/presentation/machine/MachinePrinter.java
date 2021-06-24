package eapli.base.app.backoffice.console.presentation.machine;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.visitor.Visitor;

public class MachinePrinter implements Visitor<Machine> {
    @Override
    public void visit(Machine visitee) {
        System.out.printf("%s", visitee.identity());
    }
}
