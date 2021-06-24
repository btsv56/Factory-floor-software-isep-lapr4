package eapli.base.persistence.impl.jpa;

import javax.persistence.TypedQuery;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.framework.domain.model.general.Designation;

public class JpaProductionOrderRepository extends BasepaRepositoryBase<ProductionOrder, Designation,Designation> implements ProductionOrderRepository {
    public JpaProductionOrderRepository() {
        super("internalCode");
    }

    @Override
    public Iterable<ProductionOrder> getPendingOrSuspended(){
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.state = 'Suspended' OR p.state = 'Pending'", ProductionOrder.class
        );
        return query.getResultList();
    }

    @Override
    public ProductionOrder getProductionOrderByLot(Designation lotCode) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.lot.lotCode=:p",ProductionOrder.class
        );
        query.setParameter("p", lotCode);
        return query.getSingleResult();
    }

    @Override
    public Iterable<ProductionOrder> getProductionOrdersByRequest(Designation requestCode) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.request.requestCode=:s",ProductionOrder.class
        );
        query.setParameter("s", requestCode);
        return query.getResultList();
    }

    @Override
    public Iterable<ProductionOrder> getProductionOrdersWithState(String state) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p where p.state.state=:s",ProductionOrder.class
        );
        query.setParameter("s", state);
        return query.getResultList();
    }

    @Override
    public Iterable<ProductionOrder> getUnprocessedProductionOrdersBy(ProductionLine productionLine) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p WHERE p.processingState='UNPROCESSED' AND p.productionLine=:pl",ProductionOrder.class
        );
        query.setParameter("pl", productionLine);
        return query.getResultList();
    }
}
