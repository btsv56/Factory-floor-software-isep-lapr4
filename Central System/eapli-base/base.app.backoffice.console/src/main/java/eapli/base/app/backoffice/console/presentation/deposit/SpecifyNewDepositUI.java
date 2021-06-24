package eapli.base.app.backoffice.console.presentation.deposit;

import java.util.Iterator;

import eapli.base.app.backoffice.console.presentation.product.ProductPrinter;
import eapli.base.app.backoffice.console.presentation.rawmaterial.RawMaterialPrinter;
import eapli.base.depositmanagement.application.SpecifyNewDepositController;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.application.Controller;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class SpecifyNewDepositUI extends AbstractUI {

    private final SpecifyNewDepositController controller = new SpecifyNewDepositController();

    protected Controller controller() { return this.controller; }

    @Override
    protected boolean doShow() {
        final String code= Console.readLine("Deposit code:");
        final String description= Console.readLine("Deposit description:");
        this.controller.specifyNewDeposit(code, description);
        final Iterable<RawMaterial> materials = controller.getMaterials();
        int choice;
        do {
            choice=Console.readInteger("Add new material (1); Exit(0)");
            if (choice==1) {
                final SelectWidget<RawMaterial> selector = new SelectWidget<>("Material:", materials,
                        new RawMaterialPrinter());
                selector.show();
                final RawMaterial material = selector.selectedElement();
                if (material != null) {
                    if (!controller.addDepositMaterialSheet(material)) {
                        System.out.println("Material has already been added.");
                    } else {
                        removeMaterial(material, materials);
                        System.out.println("Material added to deposit sheet.");
                    }
                }
            }
        } while (choice!=0);
        final Iterable<Product> products = controller.getProducts();
        do {
            choice=Console.readInteger("Add product (1); Exit(0)");
            if (choice==1) {
                final SelectWidget<Product> selector = new SelectWidget<>("Product:", products,
                        new ProductPrinter());
                selector.show();
                final Product product = selector.selectedElement();
                if (product != null) {
                    if (!controller.addDepositProductSheet(product)) {
                        System.out.println("Failed to add product.");
                    } else {
                        removeProduct(product, products);
                        System.out.println("Product added to deposit sheet.");
                    }
                }
            }
        } while (choice!=0);
        try{
            controller.save();
            System.out.println("Deposit added");
        }catch(@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Code already in use");
        }catch(final IllegalArgumentException e) {
            System.out.println("Invalid code or description for deposit");
        }catch(Exception e) {
            System.out.println("Error adding new deposit");
        }
        return false;
    }

    private void removeMaterial(RawMaterial material, Iterable<RawMaterial> materials) {
        Iterator<RawMaterial> ri = materials.iterator();
        while (ri.hasNext()) {
            RawMaterial r = ri.next();
            if (r.equals(material)) {
                ri.remove();
                break;
            }
        }
    }

    private void removeProduct(Product product, Iterable<Product> products) {
        Iterator<Product> pi = products.iterator();
        while (pi.hasNext()) {
            Product p = pi.next();
            if (p.equals(product)) {
                pi.remove();
                break;
            }
        }
    }

    @Override
    public String headline() {
        return "Specify new deposit";
    }
}
