package eapli.base.infrastructure.smoketests.backoffice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Invariants;

public class ProductionLineCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(DepositCRUDSmokeTester.class);
    private final ProductionLineRepository repo = PersistenceContext.repositories().productionLine();

    public void testProductionLines() {

        //save
        repo.save(new ProductionLine(Designation.valueOf("first"),"first desc"));
        repo.save(new ProductionLine(Designation.valueOf("second"),"second desc"));
        LOGGER.info("»»» created production lines");

        //findAll
        final Iterable<ProductionLine> l= repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all production lines");

        //count
        final long n = repo.count();
        LOGGER.info("»»» # production lines = {}", n);

        //ofIdentity
        final ProductionLine rm1=repo.ofIdentity(Designation.valueOf("first")).orElseThrow(IllegalStateException::new);
        final ProductionLine rm2=repo.ofIdentity(Designation.valueOf("second")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found production lines of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(rm1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains production line of identity");

        // contains
        final boolean has = repo.contains(rm1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains production line");

        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete production line");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        LOGGER.info("»»» delete production line of identity");

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # production lines = {}", n2);
    }
}
