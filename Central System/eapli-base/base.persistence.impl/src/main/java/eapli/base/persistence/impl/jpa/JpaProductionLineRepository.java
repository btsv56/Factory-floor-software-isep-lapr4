package eapli.base.persistence.impl.jpa;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.domain.model.general.Designation;

public class JpaProductionLineRepository extends BasepaRepositoryBase<ProductionLine,Designation,Designation> implements ProductionLineRepository {
    public JpaProductionLineRepository() {
        super("internalCode");
    }
}
