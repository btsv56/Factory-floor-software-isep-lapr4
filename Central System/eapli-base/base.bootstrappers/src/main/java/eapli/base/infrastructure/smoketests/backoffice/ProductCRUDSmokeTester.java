/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Invariants;

/**
 *
 * @author bruno
 */
public class ProductCRUDSmokeTester {
    
    private static final Logger LOGGER = LogManager.getLogger(ProductCRUDSmokeTester.class);
    private final ProductRepository repo = PersistenceContext.repositories().products();
    
    final private Designation ID1 = Designation.valueOf("one");
    final private Designation ID2 = Designation.valueOf("two");
    
    public void testProductCRUD() {
        // save
        final Product p1 = new Product(ID1, "one", "aaa", "aaa", "aaa", "aaa");
        final Product p2 = new Product(ID2, "two", "aaa", "aaa", "aaa", "aaa");
        repo.save(p1);
        repo.save(p2);
        LOGGER.info("»»» created products");

        // findAll
        final Iterable<Product> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all products");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # products = {}", n);

        // ofIdentity
        final Product o1 = repo.ofIdentity(ID1).orElseThrow(IllegalStateException::new);
        final Product o2 = repo.ofIdentity(ID2).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found products of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(o1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains product of identity");

        // contains
        final boolean has = repo.contains(o1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains product");

        // delete
        repo.delete(o1);
        LOGGER.info("»»» delete products");

        // deleteOfIdentity
        repo.deleteOfIdentity(o2.identity());
        LOGGER.info("»»» delete products of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # products = {}", n2);
    }
    
}
