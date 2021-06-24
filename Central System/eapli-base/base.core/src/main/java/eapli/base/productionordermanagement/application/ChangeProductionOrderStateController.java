package eapli.base.productionordermanagement.application;

import javax.persistence.RollbackException;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ChangeProductionOrderStateController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderRepository repository = PersistenceContext.repositories().productionOrder();

    public ChangeProductionOrderStateController() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
    }

    public Iterable<ProductionOrder> getPendingOrSuspended() {
        return repository.getPendingOrSuspended();
    }

    public boolean changeState(ProductionOrder prodOrder) {
        if (prodOrder.getState().isPending()) prodOrder.getState().setSuspended();
        else prodOrder.getState().setPending();
        try {
            repository.save(prodOrder);
        } catch (RollbackException ole) {
            System.out.println("Error in updating production order.");
            return false;
        }
        return true;
    }

}
