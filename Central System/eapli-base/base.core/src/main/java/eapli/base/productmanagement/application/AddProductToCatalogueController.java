package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.domain.ProductBuilder;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class AddProductToCatalogueController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductRepository repository = PersistenceContext.repositories().products();


    /**
     * Controller that controls the steps to add a product to the catalogue
     * @param fabricationCode
     * @param comercialCode
     * @param briefDescription
     * @param completeDescription
     * @param category
     * @param unity
     * @return
     */
    public Product addProductToCatalogue(String fabricationCode, String comercialCode,
                                         String briefDescription, String completeDescription,
                                         String category, String unity){

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        final ProductBuilder productbuilder = new ProductBuilder();

        productbuilder.withoutProductionSheet(Designation.valueOf(fabricationCode),
                                              comercialCode, briefDescription, completeDescription,
                                              category, unity);

        return this.repository.save(productbuilder.build());
    }

}
