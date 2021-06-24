package eapli.base.productionsheetmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListProductionSheetsService {

    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final ProductionSheetRepository repo= PersistenceContext.repositories().productionSheets();

    /**
     * All Deposits
     *
     * @return all deposits
     */
    public Iterable<ProductionSheet> allProductionSheets() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER,BaseRoles.PRODUCTION_MANAGER);
        return this.repo.findAll();
    }
}
