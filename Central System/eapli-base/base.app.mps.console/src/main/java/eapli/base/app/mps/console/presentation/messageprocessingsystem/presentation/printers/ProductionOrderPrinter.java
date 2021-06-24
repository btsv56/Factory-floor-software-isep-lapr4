package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.printers;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.visitor.Visitor;

public class ProductionOrderPrinter implements Visitor<ProductionOrder> {

    @Override
    public void visit(ProductionOrder visitee) {
        System.out.printf("%s", visitee.toStringSimple());

    }
    
}