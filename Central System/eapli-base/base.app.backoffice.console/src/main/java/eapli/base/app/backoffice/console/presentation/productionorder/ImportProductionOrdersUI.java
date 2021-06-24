package eapli.base.app.backoffice.console.presentation.productionorder;

import java.io.IOException;

import eapli.base.productionordermanagement.application.ImportProductionOrdersController;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ImportProductionOrdersUI extends AbstractUI {

    private final ImportProductionOrdersController theController = new ImportProductionOrdersController();

    protected Controller controller(){
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        String filename;
        String fileFormat;
        do {
            filename = Console.readLine("Enter the name of the file to import (filename.format)");
            fileFormat = findFormat(filename);
            if (fileFormat == null) {
                System.out.println("File name is invalid.");
            }
        } while (fileFormat==null);
        try {
            final Integer nProducts = this.theController.importProductionOrders("./files/productionorders/"+filename, fileFormat);
            System.out.println(nProducts +" production orders imported successfully!");
        } catch (IOException e) {
            System.out.println("File Format is not supported");
        } catch (Exception e) {
            System.out.println("Error importing");
        }
        return false;
    }

    private String findFormat(String filename) {
        for (int i=filename.length()-1;i>0;i--) {
            if (filename.charAt(i)=='.') return filename.substring(i+1);
        }
        return null;
    }

    @Override
    public String headline() {
        return "Import Production Orders via File";
    }
}
