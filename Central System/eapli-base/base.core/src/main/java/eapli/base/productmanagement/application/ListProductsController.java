package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.application.Controller;

public class ListProductsController implements Controller {

    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public Iterable<Product> getProducts(){
        return productRepository.findAll();
    }

}
