/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import static eapli.base.infrastructure.bootstrapers.TestDataConstants.PLINE_1;
import static eapli.base.infrastructure.bootstrapers.TestDataConstants.PORDER_1;
import static eapli.base.infrastructure.bootstrapers.TestDataConstants.PRODUCTION_SHEET_1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.application.SpecifyNewProductionOrderController;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.framework.actions.Action;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;

/**
 *
 * @author bruno
 */
public class ProductionOrderBootstrapper implements Action {

    private static final Logger LOGGER = LogManager.getLogger(ProductionOrderBootstrapper.class);
    private final ProductionSheetRepository productSheetRepostiory = PersistenceContext.repositories().productionSheets();
    private final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLine();

    private ProductionSheet getProductionSheet(final String name) {
        return productSheetRepostiory.ofIdentity(Designation.valueOf(name)).orElseThrow(IllegalStateException::new);
    }

    private ProductionLine getProductionLine(final String name){
        return productionLineRepository.ofIdentity(Designation.valueOf(name)).orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean execute() {
        ProductionSheet sheet = getProductionSheet(PRODUCTION_SHEET_1);
        ProductionLine toProductionLine = getProductionLine(PLINE_1);
        register(Designation.valueOf(PORDER_1), "po1", false, Designation.valueOf("LO1"), Designation.valueOf("1"), "20-12-2020", 10, sheet, toProductionLine);

        return true;
    }

    private void register(Designation code, String desc, boolean suspended, Designation lot,
            Designation request, String execDate, int quantity, ProductionSheet prodSheet, ProductionLine toProductionLine) {
        final SpecifyNewProductionOrderController controller = new SpecifyNewProductionOrderController();
        try {
            ProductionOrder order = controller.addProductionOrderToCatalogue(code, desc, suspended, lot, request, execDate, quantity, prodSheet);
            controller.assign(order, toProductionLine);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)");
            LOGGER.trace("Assuming existing record", e);
        }
    }

}
