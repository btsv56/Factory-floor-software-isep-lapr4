package eapli.base.persistence.impl.inmemory;

import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductRepository extends InMemoryDomainRepository<Designation, Product> implements ProductRepository {

    static{
        InMemoryInitializer.init();
    }

    public Iterable<Product> findProductsWithoutProductionSheet(){
        return match(e -> e.hasProductionSheet());
    }

}
