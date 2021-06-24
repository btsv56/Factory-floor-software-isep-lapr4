package eapli.base.app.backoffice.console.presentation.product;


import eapli.base.productmanagement.application.CheckProductsWithoutProductionSheetController;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class CheckProductsWithoutProductionSheetUI extends AbstractListUI<Product> {

    private CheckProductsWithoutProductionSheetController theController = new CheckProductsWithoutProductionSheetController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected Iterable<Product> elements() {
        return this.theController.listProductsWithoutProductionSheet();
    }

    @Override
    protected Visitor<Product> elementPrinter() {
        return new ProductPrinter();
    }

    @Override
    protected String elementName() {
        return "Product";
    }

    @Override
    protected String listHeader() {
        return "Products without Production Sheet";
    }

}
