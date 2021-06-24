package eapli.base.productionordermanagement.application;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderBuilder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.productionsheetmanagement.application.ListProductionSheetsController;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class SpecifyNewProductionOrderController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListProductionSheetsController pcs= new ListProductionSheetsController();
    private final ProductionOrderRepository por = PersistenceContext.repositories().productionOrder();

    public Iterable<ProductionSheet> getProductionSheets() {
        return this.pcs.productionSheets();
    }

    public ProductionOrder addProductionOrderToCatalogue(final Designation code, final String desc,
            final boolean suspended, final Designation lot, final Designation request, final String execDate,
            final int quantity, final ProductionSheet prodSheet) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        final ProductionOrderBuilder pob = new ProductionOrderBuilder();
        String state;
        if (suspended)
            state = "Suspended";
        else
            state = "Pending";
        final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        pob.gatherVariablesWithSheet(code, desc, state, lot, request, sdf.format(Calendar.getInstance().getTime()),
                execDate, quantity, prodSheet);
        return this.por.save(pob.build());
    }

    public ProductionOrder assign(final ProductionOrder productionOrder, final ProductionLine toProductionLine) {
        productionOrder.assign(toProductionLine);
        return this.por.save(productionOrder);
    }
}
