package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DepositCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(DepositCRUDSmokeTester.class);
    private final DepositRepository repo = PersistenceContext.repositories().deposit();

    public void testDeposit() {
        
        //save
        repo.save(new Deposit(Designation.valueOf("first"),"first desc"));
        repo.save(new Deposit(Designation.valueOf("second"),"second desc"));
        LOGGER.info("»»» created deposits");

        //findAll
        final Iterable<Deposit> l= repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all deposits");

        //count
        final long n = repo.count();
        LOGGER.info("»»» # deposits = {}", n);

        //ofIdentity
        final Deposit rm1=repo.ofIdentity(Designation.valueOf("first")).orElseThrow(IllegalStateException::new);
        final Deposit rm2=repo.ofIdentity(Designation.valueOf("second")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found deposits of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(rm1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains deposit of identity");

        // contains
        final boolean has = repo.contains(rm1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains deposit");

        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete deposit");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        LOGGER.info("»»» delete deposit of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # deposits = {}", n2);
    }

}