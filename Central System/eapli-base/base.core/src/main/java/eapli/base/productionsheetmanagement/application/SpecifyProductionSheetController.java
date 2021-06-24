package eapli.base.productionsheetmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.domain.ProductionSheetBuilder;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineProduct;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineRawMaterial;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineProductRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineRawMaterialRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;


public class SpecifyProductionSheetController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterial();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();
    private final ProductionSheetRepository productionSheetRepository = PersistenceContext.repositories().productionSheets();
    private final ProductionSheetLineProductRepository productionSheetLineProductRepository = PersistenceContext.repositories().productionSheetLinesProducts();
    private final ProductionSheetLineRawMaterialRepository productionSheetLineRawMaterialRepository = PersistenceContext.repositories().productionSheetLinesMaterials();
    private final ProductionSheetBuilder productionSheetBuilder = new ProductionSheetBuilder();

    /**M
     * Makes sure the User is authenticated
     */
    public void newSpecification(){
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
    }

    /**
     * Returns the list of Raw Materials
     * @return
     */
    public Iterable<RawMaterial> findRawMaterials(){
        return rawMaterialRepository.findAll();
    }

    /**
     * Retunrs the list of Products
     * @return
     */
    public Iterable<Product> findProducts(){
        return productRepository.findAll();
    }

    /**
     * Returns the list of products without a production sheet reference
     * @return
     */
    public Iterable<Product> findProductsWithoutProductionSheet() { return productRepository.findProductsWithoutProductionSheet();};

    /**
     * Adds the production Sheet ID to the Production Sheet Builder
     * @param productionSheetID
     */
    public void addProductionSheetID(String productionSheetID){
        productionSheetBuilder.addProductionSheetID(productionSheetID);
    }

    /**
     * adds a Selected Product to the list
     * @param item
     * @param quantity
     */
    public void addProductToTheList(Product item, Integer quantity){
        ProductionSheetLineProduct psl = new ProductionSheetLineProduct(item, quantity);
        productionSheetBuilder.addProductionSheetLine(psl);
        productionSheetLineProductRepository.save(psl);
    }

    /**
     * adds a Selected Product to the list
     * @param item
     * @param quantity
     */
    public void addMaterialToTheList(RawMaterial item, Integer quantity){
        ProductionSheetLineRawMaterial psl = new ProductionSheetLineRawMaterial(item, quantity);
        productionSheetBuilder.addProductionSheetMaterial(psl);
        productionSheetLineRawMaterialRepository.save(psl);
    }

    /**
     * Creates the given objects and saves them in the repository
     */
    public void specifyProductionSheet(Product product){
        ProductionSheet ps = productionSheetBuilder.build();
        product.addProductionSheet(productionSheetRepository.save(ps));
        productRepository.save(product);
    }

}
