/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.depositmanagement.application.SpecifyNewDepositController;
import eapli.base.infrastructure.bootstrapers.TestDataConstants;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

public class DepositBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(DepositBootstrapper.class);

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

        register(TestDataConstants.DEPOSIT_1, "Deposit 1", softWood, null);
        register(TestDataConstants.DEPOSIT_2, "Deposit 2", hardWood, null);
        register(TestDataConstants.DEPOSIT_3, "Deposit 3", iron, null);
        register(TestDataConstants.DEPOSIT_4, "Deposit 4", null, rato);
        register(TestDataConstants.DEPOSIT_5, "Deposit 5", steel, macaneta);

        return true;
    }

    private void register(final String code, final String desc, RawMaterial material, Product product) {
        final SpecifyNewDepositController controller = new SpecifyNewDepositController();
        try {
            controller.specifyNewDeposit(code, desc);
            if (material!=null) controller.addDepositMaterialSheet(material);
            if (product!=null) controller.addDepositProductSheet(product);
            controller.save();
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", desc);
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
