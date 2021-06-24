package eapli.base.persistence.impl.inmemory;

import eapli.base.depositmanagement.domain.DepositProductSheet;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryDepositProductSheetRepository extends InMemoryDomainRepository<Integer, DepositProductSheet> implements DepositProductSheetRepository {

    static {
        InMemoryInitializer.init();
    }
}
