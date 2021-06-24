/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.application.AddRawMaterialToCatalogueController;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Utilizador
 */
public class RawMaterialBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(RawMaterialBootstrapper.class);

    private final RawMaterialCategoryRepository rawMaterialCategoryRepository = PersistenceContext.repositories().rawMaterialCategory();

    private RawMaterialCategory getRawMaterialCategoryType(final String name) {
        return rawMaterialCategoryRepository.ofIdentity(name).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        final RawMaterialCategory wood = getRawMaterialCategoryType(TestDataConstants.RAW_MATERIAL_CATEGORY_WOOD);
        final RawMaterialCategory plastic = getRawMaterialCategoryType(TestDataConstants.RAW_MATERIAL_CATEGORY_PLASTIC);
        final RawMaterialCategory metal = getRawMaterialCategoryType(TestDataConstants.RAW_MATERIAL_CATEGORY_METAL);
        
        register(TestDataConstants.RAW_MATERIAL_1, "softwood", wood, "softwood.pdf", ".///files/technicalsheets/softwood.pdf");
        register(TestDataConstants.RAW_MATERIAL_2, "hardwood", wood, "hardwood.pdf", ".///files/technicalsheets/hardwood.pdf");
        register(TestDataConstants.RAW_MATERIAL_3, "plastic bottle",plastic,"platic bottle.pdf",".///files/technicalsheets/platic bottle.pdf");
        register(TestDataConstants.RAW_MATERIAL_4, "polyamide",plastic,"polyamide.pdf",".///files/technicalsheets/polyamide.pdf");
        register(TestDataConstants.RAW_MATERIAL_5, "steel",metal,"steel.pdf",".///files/technicalsheets/steel.pdf");
        register(TestDataConstants.RAW_MATERIAL_6, "iron",metal,"iron.pdf",".///files/technicalsheets/iron.pdf");

        return true;
    }

    /**
     *
     */
    private void register(final String internalCode, final String description, final RawMaterialCategory rawMaterialCategory,
             final String technicalSheetName, final String technicalSheetPath) {
        final AddRawMaterialToCatalogueController controller = new AddRawMaterialToCatalogueController();
        try {
            controller.addRawMaterialToCatalogue(internalCode, description, rawMaterialCategory, technicalSheetName, technicalSheetPath);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", description);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
