package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.application.Controller;

public class CheckProductsWithoutProductionSheetController implements Controller {

    private final ProductRepository products = PersistenceContext.repositories().products();

    public Iterable<Product> listProductsWithoutProductionSheet(){
        return products.findProductsWithoutProductionSheet();
    }

}
