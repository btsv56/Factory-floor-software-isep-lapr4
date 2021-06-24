package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.printers;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.visitor.Visitor;

public class ProductionLinePrinter implements Visitor<ProductionLine> {

    @Override
    public void visit(ProductionLine visitee) {
        System.out.printf("%s", visitee.toStringSimple());
    }
    
}