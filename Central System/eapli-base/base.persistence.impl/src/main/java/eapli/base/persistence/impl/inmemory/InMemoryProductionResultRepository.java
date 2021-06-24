package eapli.base.persistence.impl.inmemory;

import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.repositories.ProductionResultRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionResultRepository extends InMemoryDomainRepository<Long, ProductionResult> implements ProductionResultRepository {

    static {
        InMemoryInitializer.init();
    }
}
