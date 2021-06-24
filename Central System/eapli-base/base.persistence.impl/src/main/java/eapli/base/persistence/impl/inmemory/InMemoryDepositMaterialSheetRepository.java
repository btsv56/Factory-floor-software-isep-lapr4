package eapli.base.persistence.impl.inmemory;

import eapli.base.depositmanagement.domain.DepositMaterialSheet;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryDepositMaterialSheetRepository extends InMemoryDomainRepository<Integer, DepositMaterialSheet> implements DepositMaterialSheetRepository {

    static {
        InMemoryInitializer.init();
    }
}
