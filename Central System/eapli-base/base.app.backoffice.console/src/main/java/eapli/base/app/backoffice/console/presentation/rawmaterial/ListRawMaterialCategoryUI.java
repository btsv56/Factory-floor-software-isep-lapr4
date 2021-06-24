/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.ListRawMaterialCategoriesController;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class ListRawMaterialCategoryUI extends AbstractListUI<RawMaterialCategory> {

    final ListRawMaterialCategoriesController theController = new ListRawMaterialCategoriesController();

    @Override
    protected Iterable<RawMaterialCategory> elements() {
        return this.theController.allRawMaterialsCategory();
    }

    @Override
    protected Visitor<RawMaterialCategory> elementPrinter() {
        return new RawMaterialCategoryPrinter();
    }

    @Override
    protected String elementName() {
        return "Raw Material Category";
    }

    @Override
    protected String listHeader() {
        return "Raw Material Categories";
    }

}

