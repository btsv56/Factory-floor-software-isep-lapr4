package eapli.base.persistence.impl.inmemory;

import eapli.base.productionsheetmanagement.domain.ProductionSheetLineProduct;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineProductRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionSheetLineProductRepository extends InMemoryDomainRepository<Integer, ProductionSheetLineProduct> implements ProductionSheetLineProductRepository {

    static {
        InMemoryInitializer.init();
    }

}

