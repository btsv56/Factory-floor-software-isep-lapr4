/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.rawmaterial;

import eapli.base.rawmaterialmanagement.application.AddRawMaterialToCatalogueController;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

/**
 *
 * @author Utilizador
 */
public class AddRawMaterialToCatalogueUI extends AbstractUI {

    private final AddRawMaterialToCatalogueController theController = new AddRawMaterialToCatalogueController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        final Iterable<RawMaterialCategory> rawMaterialCategories = this.theController.getRawMaterialCategories();

        final SelectWidget<RawMaterialCategory> selector = new SelectWidget<>("Raw Material categories:", rawMaterialCategories,
                new RawMaterialCategoryPrinter());
        selector.show();
        final RawMaterialCategory theRawMaterialCategory = selector.selectedElement();
        if(theRawMaterialCategory == null) return false;
        final String internalCode = Console.readLine("Internal code");
        final String description = Console.readLine("Description");
        final String nameTechnicalSheet = Console.readLine("Name of technical sheet");
        try {
            this.theController.addRawMaterialToCatalogue(internalCode, description, theRawMaterialCategory, nameTechnicalSheet, "./files/technicalsheets/"+nameTechnicalSheet);
            System.out.println("Raw material added");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Raw material exists");
        }catch(final IllegalArgumentException e) {
            System.out.println(e);
        }catch(Exception e) {
            System.out.println("Error adding new raw material");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Add Raw Material to catalogue";
    }

}
