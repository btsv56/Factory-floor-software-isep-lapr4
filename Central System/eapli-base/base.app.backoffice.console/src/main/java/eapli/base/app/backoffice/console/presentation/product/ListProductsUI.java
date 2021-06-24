package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.application.ListProductsController;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListProductsUI extends AbstractListUI<Product> {

    final ListProductsController theController = new ListProductsController();

    @Override
    protected Iterable<Product> elements() {
        return this.theController.getProducts();
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
        return "Products";
    }
}
