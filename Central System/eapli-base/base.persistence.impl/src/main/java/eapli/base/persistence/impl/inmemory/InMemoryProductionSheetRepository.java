package eapli.base.persistence.impl.inmemory;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionSheetRepository extends InMemoryDomainRepository<Designation, ProductionSheet> implements ProductionSheetRepository {

    static{
        InMemoryInitializer.init();
    }
}
