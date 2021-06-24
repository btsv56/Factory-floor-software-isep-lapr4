package eapli.base.app.backoffice.console.presentation.productionline;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.visitor.Visitor;

public class ProductionLinePrinter implements Visitor<ProductionLine> {

    public void visit(final ProductionLine visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
