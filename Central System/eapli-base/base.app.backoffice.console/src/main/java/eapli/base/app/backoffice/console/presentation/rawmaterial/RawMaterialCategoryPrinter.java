/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
@SuppressWarnings("squid:S106")
class RawMaterialCategoryPrinter implements Visitor<RawMaterialCategory> {

    @Override
    public void visit(final RawMaterialCategory visitee) {
        System.out.printf("%s - %s", visitee.identity(), visitee.description());
    }
    
}
