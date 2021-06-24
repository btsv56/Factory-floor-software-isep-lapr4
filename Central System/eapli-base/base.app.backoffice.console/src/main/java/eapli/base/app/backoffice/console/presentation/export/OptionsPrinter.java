/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.export;

import eapli.framework.visitor.Visitor;

/**
 *
 * @author Utilizador
 */
public class OptionsPrinter implements Visitor<String>{

    @Override
    public void visit(String visitee) {
        System.out.print(visitee);
    }
    
}
