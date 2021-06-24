package eapli.base.persistence.impl.inmemory;

import eapli.base.productionsheetmanagement.domain.ProductionSheetLineRawMaterial;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineRawMaterialRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionSheetLineRawMaterialRepository extends InMemoryDomainRepository<Integer, ProductionSheetLineRawMaterial> implements ProductionSheetLineRawMaterialRepository {

    static {
        InMemoryInitializer.init();
    }

}

