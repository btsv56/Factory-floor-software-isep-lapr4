/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.scm.console.presentation;

/**
 *
 * @author Utilizador
 */
public class ImportMessageFilesAction implements Runnable{

    @Override
    public void run() {
        new ImportMessageFilesUI().importMessageFiles();
    }
    
}
