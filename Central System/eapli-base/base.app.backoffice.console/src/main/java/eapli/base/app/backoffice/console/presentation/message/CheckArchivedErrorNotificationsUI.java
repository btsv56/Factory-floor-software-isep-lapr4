package eapli.base.app.backoffice.console.presentation.message;

import eapli.base.app.backoffice.console.presentation.export.OptionsPrinter;
import eapli.base.app.backoffice.console.presentation.machine.MachinePrinter;
import eapli.base.errornotificationmanagement.application.ConsultArchivedProcessedErrorNotificationsController;
import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckArchivedErrorNotificationsUI extends AbstractListUI<ErrorNotification> {
    final ConsultArchivedProcessedErrorNotificationsController theController = new ConsultArchivedProcessedErrorNotificationsController();

    @Override
    protected Iterable<ErrorNotification> elements() {
        final List<String> options = new ArrayList<>();
        options.add("Machine");
        options.add("Time");

        final SelectWidget<String> filterSelector = new SelectWidget<>("Select a Filter:", options,
                new OptionsPrinter());
        filterSelector.show();
        
        if(filterSelector.selectedElement() == null) return Collections.EMPTY_LIST;

        if (filterSelector.selectedElement().equals("Machine")) {
            SelectWidget<Machine> machineSelector = new SelectWidget<>("Select a Machine", theController.findMachines(), new MachinePrinter());
            machineSelector.show();
            return theController.listErrorNotificationsByMachine(machineSelector.selectedElement());
        }
        if (filterSelector.selectedElement().equals("Time")) {
            return theController.listErrorNotificationsByTime();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Visitor<ErrorNotification> elementPrinter() {
        return new ErrorNotificationPrinter();
    }

    @Override
    protected String elementName() {
        return "Error Notification";
    }

    @Override
    protected String listHeader() {
        return "Error Notifications";
    }

}
