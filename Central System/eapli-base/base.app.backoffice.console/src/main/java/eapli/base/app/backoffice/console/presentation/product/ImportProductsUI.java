package eapli.base.app.backoffice.console.presentation.product;

import eapli.base.productmanagement.application.ImportProductsController;
import eapli.base.productmanagement.imports.FileFormat;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.io.IOException;

public class ImportProductsUI extends AbstractUI {

    private final ImportProductsController theController = new ImportProductsController();

    protected Controller controller(){
        return this.theController;
    }

    @Override
    protected boolean doShow() {

        final String name = Console.readLine("Enter the name of the File to import");
        final String format = findFormat(name);
        try {
            final Integer nProducts = this.theController.importProducts("./files/products/"+name, FileFormat.CSV);
            System.out.println(nProducts +" products imported successfully!");
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
        return "Import Products via File";
    }
}
