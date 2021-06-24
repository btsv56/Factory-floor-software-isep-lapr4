/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.rawmaterialmanagement.application.AddRawMaterialCategoryController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 *
 * @author Utilizador
 */
public class RawMaterialCategoryBootstrapper implements Action {
    private static final Logger LOGGER = LogManager.getLogger(RawMaterialCategoryBootstrapper.class);

    @Override
    public boolean execute() {
        register(TestDataConstants.RAW_MATERIAL_CATEGORY_WOOD, "wood");
        register(TestDataConstants.RAW_MATERIAL_CATEGORY_PLASTIC, "plastic");
        register(TestDataConstants.RAW_MATERIAL_CATEGORY_METAL, "metal");
        return true;
    }

    /**
     *
     */
    private void register(final String name, final String description) {
        final AddRawMaterialCategoryController controller = new AddRawMaterialCategoryController();
        try {
            controller.registerRawMaterialCategory(name, description);
        } catch (final IntegrityViolationException | ConcurrencyException ex) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", name);
            LOGGER.trace("Assuming existing record", ex);
        }
    }
}

