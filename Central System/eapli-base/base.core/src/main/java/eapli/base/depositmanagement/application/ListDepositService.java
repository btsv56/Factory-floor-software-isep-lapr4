package eapli.base.depositmanagement.application;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListDepositService {

    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final DepositRepository repo= PersistenceContext.repositories().deposit();

    /**
     * All Deposits
     *
     * @return all deposits
     */
    public Iterable<Deposit> allDeposits() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER);
        return this.repo.findAll();
    }
}
