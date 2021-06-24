package eapli.base.app.backoffice.console.presentation.productionsheet;

import eapli.base.app.backoffice.console.presentation.product.ProductPrinter;
import eapli.base.app.backoffice.console.presentation.rawmaterial.RawMaterialPrinter;
import eapli.base.productionsheetmanagement.application.SpecifyProductionSheetController;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.util.Iterator;

public class SpecifyProductionSheetUI extends AbstractUI {

    private final SpecifyProductionSheetController theController = new SpecifyProductionSheetController();

    protected Controller controller() {
        return this.theController;
    }

    private final Iterable<RawMaterial> rawMaterials = this.theController.findRawMaterials();
    private Iterable<Product> products = this.theController.findProducts();


    @Override
    protected boolean doShow() {
        this.theController.newSpecification();

        final String productionSheetID = Console.readLine("Enter The Production Sheet ID");
        this.theController.addProductionSheetID(productionSheetID);

        Integer quantity;

        do {
            SelectWidget<RawMaterial> rawMaterialSelector = new SelectWidget<>("Select Raw Materials", rawMaterials, new RawMaterialPrinter());
            rawMaterialSelector.show();
            if(rawMaterialSelector.selectedElement() == null) { return false; }
            quantity = Console.readInteger("Enter the respective quantity");
            this.theController.addMaterialToTheList(rawMaterialSelector.selectedElement(), quantity);
            removeRawMaterial(rawMaterialSelector.selectedElement());
        } while(Console.readInteger("'1' to add more Raw Materials '0' to exit") != 0);

        do {
            SelectWidget<Product> productSelector = new SelectWidget<>("List of Products", products, new ProductPrinter());
            productSelector.show();
            if(productSelector.selectedElement() == null) { return false; }
            quantity = Console.readInteger("Enter the respective quantity");
            this.theController.addProductToTheList(productSelector.selectedElement(), quantity);
            removeProduct(productSelector.selectedElement());
        } while(Console.readInteger("'1' to add more Products '0' to exit") != 0);


        products = this.theController.findProductsWithoutProductionSheet();
        SelectWidget<Product> productSelector = new SelectWidget<>("What Product is this Production sheet for?", products, new ProductPrinter());
        productSelector.show();
        if(productSelector.selectedElement() == null) { return false; }
        try {
            this.theController.specifyProductionSheet(productSelector.selectedElement());
            System.out.println("Production Sheet Added Successfully");
        } catch(final IllegalArgumentException e){
            System.out.println("Invalid Fields entered!");
        } catch(Exception e){
            System.out.println("Error while Specifying production sheet");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Specify a Product's production sheet";
    }

    private void removeRawMaterial(RawMaterial rawMaterial) {
        Iterator<RawMaterial> ri = rawMaterials.iterator();
        while(ri.hasNext()) {
            RawMaterial r = ri.next();
            if( r == rawMaterial){
                ri.remove();
                break;
            }
        }
    }

    private void removeProduct(Product product) {
        Iterator<Product> ri = products.iterator();
        while(ri.hasNext()) {
            Product r = ri.next();
            if( r == product){
                ri.remove();
                break;
            }
        }
    }
}
