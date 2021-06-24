package eapli.base.app.backoffice.console.presentation.productionsheet;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.framework.visitor.Visitor;

public class ProductionSheetPrinter implements Visitor<ProductionSheet> {

    public void visit(final ProductionSheet visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
