/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.bootstrapers.demo;

import static eapli.base.infrastructure.bootstrapers.TestDataConstants.*;
import eapli.base.productmanagement.application.AddProductToCatalogueController;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author bruno
 */
public class ProductBootstrapper implements Action  {
    private static final Logger LOGGER = LogManager.getLogger(ProductBootstrapper.class);

    @Override
    public boolean execute() {
        register(PRODUCT_1, PRODUCT_1, "rato", "Produto muito bem bom para fazer coisas", "informatica", "U1");
        register(PRODUCT_2, PRODUCT_2, "macaneta", "Produto mais bem bom para fazer coisas", "carpintaria", "U2");
        register(PRODUCT_3, PRODUCT_3, "Conversor RCA/AV", "Conversor RCA/AV para HDMI 1080p (novo)", "informatica", "U2");
        register(PRODUCT_4, PRODUCT_4, "liquidificador", "marca Moulinex / modelo Deposite", "cozinha", "U2");
        register(PRODUCT_5, PRODUCT_5, "Punhal decorativo", "Punhal decorativo todo trabalhador", "fantasias", "U1");
        
        return true;
    }
    
    private void register(String fabricationCode, String comercialCode, 
            String briefDescription, String completeDescription,
            String category, String unity) {
        final AddProductToCatalogueController controller = new AddProductToCatalogueController();
        try {
            controller.addProductToCatalogue(fabricationCode, comercialCode, briefDescription, completeDescription, unity, category);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)");
            LOGGER.trace("Assuming existing record", e);
        }
    }
}
