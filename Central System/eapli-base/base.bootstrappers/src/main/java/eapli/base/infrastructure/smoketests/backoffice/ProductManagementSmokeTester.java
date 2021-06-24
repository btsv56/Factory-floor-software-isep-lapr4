/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

/**
 *
 * @author bruno
 */
public class ProductManagementSmokeTester implements Action {
    
    final private ProductCRUDSmokeTester productCRUDSmokerTester = new ProductCRUDSmokeTester();

    @Override
    public boolean execute() {
        testProductCRUD();
        
        return true;
    }
    
    private void testProductCRUD(){
        productCRUDSmokerTester.testProductCRUD();
    }
    
}
