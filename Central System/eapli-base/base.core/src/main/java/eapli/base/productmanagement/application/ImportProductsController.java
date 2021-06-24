package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.imports.FileFormat;
import eapli.base.productmanagement.imports.ProductImporter;
import eapli.base.productmanagement.imports.ProductImporterFactory;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.IOException;
import java.util.List;

/**
 * Constrols the importation of a file
 */
public class ImportProductsController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductImporterFactory factory = new ProductImporterFactory();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    public int importProducts(String filename, FileFormat format) throws IOException {

        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);

        final ProductImporter importer = factory.build(format);

        final List<Product> products = importer.begin(filename);
        int cont = 0;
        for(Product p: products){
            productRepository.save(p);
            cont++;
        }
        return cont;
    }

}
