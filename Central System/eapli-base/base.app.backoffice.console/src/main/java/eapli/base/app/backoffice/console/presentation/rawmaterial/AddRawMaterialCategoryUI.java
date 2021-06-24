/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.AddRawMaterialCategoryController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.util.Console;

/**
 *
 * @author Utilizador
 */
@SuppressWarnings("java:S106")
public class AddRawMaterialCategoryUI extends AbstractUI{

    private final AddRawMaterialCategoryController theController=new AddRawMaterialCategoryController();
    
    protected Controller controller() {
        return this.theController;
    }
    
    @Override
    protected boolean doShow() {
        final String name= Console.readLine("Raw Material category name:");
        final String description= Console.readLine("Raw Material category description:");
        try{
            this.theController.registerRawMaterialCategory(name, description);
            System.out.println("Raw material category added");
        }catch(@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Name of raw material category already in use");
        }catch(final IllegalArgumentException e) {
            System.out.println("Invalid name or description for raw material category");
        }catch(Exception e) {
            System.out.println("Error adding new raw material category");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Add raw material category";
    }
    
}
