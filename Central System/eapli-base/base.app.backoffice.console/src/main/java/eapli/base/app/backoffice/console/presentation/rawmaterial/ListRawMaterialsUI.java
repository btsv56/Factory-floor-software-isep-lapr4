/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.ListRawMaterialsController;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class ListRawMaterialsUI extends AbstractListUI<RawMaterial>{
    
    final ListRawMaterialsController theController= new ListRawMaterialsController();

    @Override
    protected Iterable<RawMaterial> elements() {
        return this.theController.allRawMaterials();
    }

    @Override
    protected Visitor<RawMaterial> elementPrinter() {
        return new RawMaterialPrinter();
    }

    @Override
    protected String elementName() {
        return "Raw Material";
    }

    @Override
    protected String listHeader() {
        return "Raw Materials";
    }
}
