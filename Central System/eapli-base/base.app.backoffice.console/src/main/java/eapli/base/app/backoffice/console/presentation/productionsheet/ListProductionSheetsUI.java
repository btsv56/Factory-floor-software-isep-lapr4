package eapli.base.app.backoffice.console.presentation.productionsheet;

import eapli.base.productionsheetmanagement.application.ListProductionSheetsController;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListProductionSheetsUI extends AbstractListUI<ProductionSheet> {

    final ListProductionSheetsController theController = new ListProductionSheetsController();

    @Override
    protected Iterable<ProductionSheet> elements() {
        return this.theController.productionSheets();
    }

    @Override
    protected Visitor<ProductionSheet> elementPrinter() {
        return new ProductionSheetPrinter();
    }

    @Override
    protected String elementName() {
        return "Production Sheet";
    }

    @Override
    protected String listHeader() {
        return "Production Sheets";
    }
}
