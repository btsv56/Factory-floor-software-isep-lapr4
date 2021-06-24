package eapli.base.app.backoffice.console.presentation.productionline;

import eapli.base.productionlinemanagement.application.SpecifyNewProductionLineController;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.util.Iterator;

public class SpecifyNewProductionLineUI extends AbstractUI {
    private final SpecifyNewProductionLineController controller = new SpecifyNewProductionLineController();

    protected Controller controller() { return this.controller; }

    @Override
    protected boolean doShow() {
        final String code= Console.readLine("Production line code:");
        final String description= Console.readLine("Production line description:");
        this.controller.specifyNewProductionLine(Designation.valueOf(code), description);
        final Iterable<Machine> machines = controller.getMachines();
        int choice;
        int machs=0;
        do {
            choice=Console.readInteger("Add new machine (1); Exit(0)");
            if (choice==1) {
                final SelectWidget<Machine> selector = new SelectWidget<>("Machines:", machines,
                        new MachinePrinter());
                selector.show();
                final Machine machine = selector.selectedElement();
                if(machine != null) {
                    if (!controller.addMachine(machine)) System.out.println("Failed to add machine.");
                    else {
                        machs++;
                        removeMachine(machine, machines);
                        System.out.println("Machine added to production line.");
                    }
                }
            }
        } while (choice!=0);
        if (machs==1) {
            System.out.println("Production lines must have more than one machine.");
            return false;
        }
        try{
            controller.save();
            System.out.println("Production line added");
        }catch(@SuppressWarnings("unused") final IntegrityViolationException e) {
            System.out.println("Code already in use");
        }catch(final IllegalArgumentException e) {
            System.out.println("Invalid code or description for deposit");
        }catch(Exception e) {
            System.out.println("Error adding new deposit");
        }
        return false;
    }

    private void removeMachine(Machine machine, Iterable<Machine> machines) {
        Iterator<Machine> mi = machines.iterator();
        while (mi.hasNext()) {
            Machine m = mi.next();
            if (m.equals(machine)) {
                mi.remove();
                break;
            }
        }
    }

    @Override
    public String headline() {
        return "Specify new production line";
    }
}
