package eapli.base.productionordermanagement.application;

import java.io.IOException;
import java.util.List;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.imports.ProductionOrderImporter;
import eapli.base.productionordermanagement.imports.ProductionOrderImporterFactory;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ImportProductionOrdersController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionOrderImporterFactory factory = new ProductionOrderImporterFactory();
    private final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrder();

    public int importProductionOrders(String filename, String format) throws IOException {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);

        final ProductionOrderImporter importer = factory.build(format);

        final List<ProductionOrder> productionOrders = importer.begin(filename);
        int cont = 0;
        for(ProductionOrder p: productionOrders){
            try {
                productionOrderRepository.save(p);
                cont++;
            } catch (IllegalArgumentException iae) {
                System.out.println("Error saving production order in database.");
            }
        }
        return cont;
    }
}
