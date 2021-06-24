package eapli.base.persistence.impl.inmemory;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionLineRepository extends InMemoryDomainRepository<Designation, ProductionLine> implements ProductionLineRepository {

    static {
        InMemoryInitializer.init();
    }
}
