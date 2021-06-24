/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
@SuppressWarnings("squid:S106")
public class RawMaterialPrinter implements Visitor<RawMaterial> {
    
    @Override
    public void visit(final RawMaterial visitee) {
        System.out.printf("%s", visitee.toString());
    }
}
