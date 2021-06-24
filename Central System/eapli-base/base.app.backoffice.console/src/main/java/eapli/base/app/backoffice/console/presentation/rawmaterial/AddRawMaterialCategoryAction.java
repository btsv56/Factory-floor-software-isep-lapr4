/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.framework.actions.Action;

/**
 *
 * @author Utilizador
 */
public class AddRawMaterialCategoryAction implements Action {

    @Override
    public boolean execute() {
        return new AddRawMaterialCategoryUI().show();
    }
}
