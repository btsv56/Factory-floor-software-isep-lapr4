package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionsheetmanagement.application.SpecifyProductionSheetController;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static eapli.base.infrastructure.bootstrapers.TestDataConstants.*;

public class ProductionSheetBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProductBootstrapper.class);

    private final RawMaterialRepository materialRepository = PersistenceContext.repositories().rawMaterial();
    private final ProductRepository productRepository = PersistenceContext.repositories().products();

    private RawMaterial getMaterial(final String name) {
        return materialRepository.ofIdentity(Designation.valueOf(name)).orElseThrow(IllegalStateException::new);
    }

    private Product getProduct(final String name) {
        return productRepository.ofIdentity(Designation.valueOf(name)).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        final RawMaterial softWood = getMaterial(TestDataConstants.RAW_MATERIAL_1);
        final RawMaterial hardWood = getMaterial(TestDataConstants.RAW_MATERIAL_2);
        final RawMaterial iron = getMaterial(TestDataConstants.RAW_MATERIAL_6);
        final RawMaterial steel = getMaterial(TestDataConstants.RAW_MATERIAL_5);

        final Product rato = getProduct(TestDataConstants.PRODUCT_1);
        final Product macaneta = getProduct(TestDataConstants.PRODUCT_2);
        final Product conversor = getProduct(TestDataConstants.PRODUCT_3);
        final Product liquidificador = getProduct(TestDataConstants.PRODUCT_4);
        final List<Product> products = new ArrayList<>();
        products.add(rato);products.add(macaneta);
        final List<Product> products1 = new ArrayList<>();
        products1.add(conversor);products1.add(liquidificador);
        final List<Product> products2 = new ArrayList<>();
        products2.add(rato);products2.add(conversor);
        final List<Product> products3 = new ArrayList<>();
        final List<Product> products4 = new ArrayList<>();
        products4.add(macaneta);products4.add(liquidificador);products4.add(conversor);

        final List<RawMaterial> rawMaterials = new ArrayList<>();
        rawMaterials.add(softWood);rawMaterials.add(hardWood);
        final List<RawMaterial> rawMaterials1 = new ArrayList<>();
        rawMaterials1.add(softWood);
        final List<RawMaterial> rawMaterials2 = new ArrayList<>();
        rawMaterials2.add(softWood);rawMaterials2.add(hardWood);rawMaterials2.add(iron);
        final List<RawMaterial> rawMaterials4 = new ArrayList<>();
        rawMaterials4.add(steel);

        register(PRODUCTION_SHEET_1, products, rawMaterials, liquidificador);
        register(PRODUCTION_SHEET_2, products1, rawMaterials1, rato);
        register(PRODUCTION_SHEET_3, products2, rawMaterials2, macaneta);
        register(PRODUCTION_SHEET_4, products3, rawMaterials4, conversor);

        return true;
    }

    private void register(String productionSheetID, List<Product> productList, List<RawMaterial> rawMaterialList, Product passociate) {
        final SpecifyProductionSheetController controller = new SpecifyProductionSheetController();
        try {
            controller.addProductionSheetID(productionSheetID);
            if(!rawMaterialList.isEmpty()) {
                for (RawMaterial r : rawMaterialList) {
                    controller.addMaterialToTheList(r, 3);
                }
            }
            if(!productList.isEmpty()) {
                for (Product p : productList) {
                    controller.addProductToTheList(p, 2);
                }
            }
            controller.specifyProductionSheet(passociate);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)");
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
