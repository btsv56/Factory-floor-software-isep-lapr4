package eapli.base.productmanagement.repositories;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.DomainRepository;

public interface ProductRepository extends DomainRepository<Designation, Product> {

    Iterable<Product> findProductsWithoutProductionSheet();

}
