package eapli.base.app.backoffice.console.presentation.productionorder;

import eapli.base.app.backoffice.console.presentation.productionsheet.ProductionSheetPrinter;
import eapli.base.productionordermanagement.application.SpecifyNewProductionOrderController;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Calendars;
import eapli.framework.util.Console;

import java.util.Calendar;

public class SpecifyNewProductionOrderUI extends AbstractUI {

    private final SpecifyNewProductionOrderController controller = new SpecifyNewProductionOrderController();

    protected Controller controller() { return this.controller; }

    @Override
    protected boolean doShow() {
        final String code= Console.readLine("Production Order code:");
        final String description= Console.readLine("Production Order description:");
        final Iterable<ProductionSheet> prodSheets = controller.getProductionSheets();
        final ProductionSheet prodSheet;
        if (prodSheets.iterator().hasNext()) {
            final SelectWidget<ProductionSheet> selector = new SelectWidget<>("Production sheet:", prodSheets, new ProductionSheetPrinter());
            selector.show();
            prodSheet = selector.selectedElement();
            if (prodSheet != null) {
                System.out.println("Production sheet added to production order.");
            }
        } else { System.out.println("There are no production sheets available"); return false; }
        String expDate;
        Calendar calDate;
        do {
            expDate = Console.readLine("Expected execution date (DD-MM-YYYY):");
            try {
                calDate = Calendars.parse(expDate, "dd-MM-yyyy");
                if (calDate.compareTo(Calendars.now()) < 0) {
                    System.out.println("Date can't be from the past.");
                    expDate="";
                }
            } catch (Exception e) {
                System.out.println("Invalid date.");
                expDate = "";
            }
        } while(expDate.isEmpty());
        final String lotCode = Console.readLine("Lot code:");
        final String requestCode = Console.readLine("Request code:");
        boolean suspended=false;
        final String susStr = Console.readLine("Change state to 'suspended'? (default value is 'pending')\nY/N:");
        if (susStr.compareTo("Y")==0||susStr.compareTo("y")==0) suspended=true;
        int quantity;
        do {
            quantity = Console.readInteger("Quantity to produce:");
            if (quantity < 1) {
                System.out.println("Quantity should be above 0");
            }
        } while (quantity < 1);
        try {
            controller.addProductionOrderToCatalogue(Designation.valueOf(code), description, suspended, Designation.valueOf(lotCode), Designation.valueOf(requestCode), expDate, quantity, prodSheet);
            System.out.println("Production order added");
        } catch (@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Code already in use");
        } catch (final IllegalArgumentException e) {
            System.out.println("Invalid code or description for production order");
        } catch (Exception e) {
            System.out.println("Error adding new production order");
        }
        return false;
    }

    @Override
    public String headline() {
        return "Add a Production Order";
    }
}
