/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.application;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;

/**
 *
 * @author Utilizador
 */
public class ListRawMaterialCategoriesController {
    
    final ListRawMaterialCategoryService svc= new ListRawMaterialCategoryService();
    
    /**
     * All raw materal categories
     * 
     * @return all raw material categories
     */
    public Iterable<RawMaterialCategory> allRawMaterialsCategory() {
        return this.svc.allRawMaterialCategories();
    }
}
