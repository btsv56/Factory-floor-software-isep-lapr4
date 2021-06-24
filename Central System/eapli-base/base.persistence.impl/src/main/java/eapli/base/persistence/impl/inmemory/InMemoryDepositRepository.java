package eapli.base.persistence.impl.inmemory;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryDepositRepository extends InMemoryDomainRepository<Designation, Deposit> implements DepositRepository {

    static {
        InMemoryInitializer.init();
    }
}
