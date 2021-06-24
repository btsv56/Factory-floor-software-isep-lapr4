package eapli.base.persistence.impl.inmemory;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProcessingState;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryProductionOrderRepository extends InMemoryDomainRepository<Designation, ProductionOrder> implements ProductionOrderRepository {

    static {
        InMemoryInitializer.init();
    }

    public Iterable<ProductionOrder> getPendingOrSuspended(){
        return match(ProductionOrder::isPendingOrSuspended);
    }

    @Override
    public ProductionOrder getProductionOrderByLot(Designation lotCode) {
        return matchOne(e->e.hasLot(lotCode)).get();
    }

    @Override
    public Iterable<ProductionOrder> getProductionOrdersByRequest(Designation requestCode) { return match(e->e.hasRequest(requestCode)); }

    @Override
    public Iterable<ProductionOrder> getProductionOrdersWithState(String state) {
        return match(p->p.hasState(state));
    }

    @Override
    public Iterable<ProductionOrder> getUnprocessedProductionOrdersBy(ProductionLine productionLine) {
        return match(e -> e.processingState().equals(ProcessingState.UNPROCESSED) && e.productionLine().equals(productionLine));
    }
}
