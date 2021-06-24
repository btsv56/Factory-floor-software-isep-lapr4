package eapli.base.persistence.impl.jpa;

import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.repositories.ProductionResultRepository;

public class JpaProductionResultRepository extends BasepaRepositoryBase<ProductionResult, Long, Long> implements ProductionResultRepository {

    public JpaProductionResultRepository() {
        super("productionOrder");
    }

}
