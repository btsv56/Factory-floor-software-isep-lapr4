/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.framework.actions.Action;

/**
 *
 * @author Utilizador
 */
public class RawMaterialManagementSmokeTester implements Action{

    final RawMaterialCategoryCRUDSmokeTester rawMaterialCategorySmokeTester = new RawMaterialCategoryCRUDSmokeTester();
    final RawMaterialCRUDSmokeTester rawMaterialSmokeTester= new RawMaterialCRUDSmokeTester();
    
    @Override
    public boolean execute() {
        testRawMaterialCategoryCRUD();
        testRawMaterialCRUD();
        return true;
    }
    
    private void testRawMaterialCategoryCRUD() {
        rawMaterialCategorySmokeTester.testRawMaterialCategory();
    }

    private void testRawMaterialCRUD() {
        rawMaterialSmokeTester.testRawMaterialCategory();
    }
    
    
    
}
