package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.visitor.Visitor;

public class ProductPrinter implements Visitor<Product> {
    @Override
    public void visit(Product visitee) {
        System.out.printf("%s - %s", visitee.identity(), visitee.description());
    }
}
