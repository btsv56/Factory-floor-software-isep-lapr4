package eapli.base.productionordermanagement.repositories;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.DomainRepository;

public interface ProductionOrderRepository extends DomainRepository<Designation, ProductionOrder>  {

    Iterable<ProductionOrder> getPendingOrSuspended();
    ProductionOrder getProductionOrderByLot(Designation lotCode);
    Iterable<ProductionOrder> getProductionOrdersByRequest(Designation requestCode);
    Iterable<ProductionOrder> getProductionOrdersWithState(String state);
	Iterable<ProductionOrder> getUnprocessedProductionOrdersBy(ProductionLine productionLine);
}
