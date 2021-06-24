package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.application.AddProductToCatalogueController;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class AddProductToCatalogueUI extends AbstractUI {

    private final AddProductToCatalogueController theController = new AddProductToCatalogueController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        final String fabricationCode = Console.readLine("Fabrication Code");
        final String comercialCode = Console.readLine("Comercial Code");
        final String briefDescription = Console.readLine("Brief Description");
        final String completeDescription = Console.readLine("Complete Description");
        final String unity = Console.readLine("Unity");
        final String category = Console.readLine("Category");
        try {
            this.theController.addProductToCatalogue(fabricationCode, comercialCode,briefDescription, completeDescription, unity, category);
            System.out.println("Product Added Successfully");
        } catch(final IllegalArgumentException e){
            System.out.println("Invalid Fields entered!");
        } catch(Exception e){
            System.out.println("Error while adding a new Product");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Add A Product To Catalogue";
    }
}
