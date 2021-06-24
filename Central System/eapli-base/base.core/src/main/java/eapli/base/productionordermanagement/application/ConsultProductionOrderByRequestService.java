package eapli.base.productionordermanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


public class ConsultProductionOrderByRequestService {
    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final ProductionOrderRepository repo= PersistenceContext.repositories().productionOrder();

    public Iterable<ProductionOrder> consultByRequest(String requestCode) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.PRODUCTION_MANAGER);
        return repo.getProductionOrdersByRequest(Designation.valueOf(requestCode));
    }
}
