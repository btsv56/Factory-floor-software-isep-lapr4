package eapli.base.app.backoffice.console.presentation.errornotificationmanagement;

import java.util.LinkedList;
import java.util.List;

import eapli.base.errornotificationmanagement.application.CheckErrorNotificationsController;
import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.base.errornotificationmanagement.domain.ErrorNotificationType;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

public class CheckErrorNotificationUI extends AbstractUI {

    private final CheckErrorNotificationsController controller = new CheckErrorNotificationsController();
    private final String tab = "    ";

    @Override
    protected boolean doShow() {
        Iterable<ProductionLine> productionLines = null;
        Iterable<ErrorNotificationType> types = null;
        try {
            final boolean wantsToFilterByProductoinLine = Console
                    .readBoolean("Do you want to filter by Production Line?");
            if (wantsToFilterByProductoinLine) {
                productionLines = showProductionLines();
            }
            final boolean wantsToFilterByErrorType = Console.readBoolean("Do you want to filter by Type of Error?");
            if (wantsToFilterByErrorType) {
                types = showErrorNotificationTypes();
            }
            showErrorNotificationsFiltered(productionLines, types);
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void showErrorNotificationsFiltered(final Iterable<ProductionLine> productionLines,
            final Iterable<ErrorNotificationType> types) {
        Iterable<ErrorNotification> list = controller.listErrorNotificatoinByFilter(productionLines, types);
        System.out.println("Error Notifications:");
        for (final ErrorNotification error : list) {
            System.out.println(tab + "-> " + error.toStringHorizontal());
        }
        Console.readLine("Press Enter to exit.");
    }

    private Iterable<ErrorNotificationType> showErrorNotificationTypes() {
        final List<ErrorNotificationType> filterList = new LinkedList<>();
        final List<ErrorNotificationType> list = (List<ErrorNotificationType>)controller.listErrorNotificationTypes();
        SelectWidget<ErrorNotificationType> selector = null;
        boolean added = false;
        do {
            selector = new SelectWidget<>(
                "Which error types do you want to filter?", list);
            selector.show();
            added = add(selector.selectedElement(), filterList);
            list.remove(selector.selectedElement());
        } while (added && !list.isEmpty());
        return filterList;
    }

    private Iterable<ProductionLine> showProductionLines() {
        final List<ProductionLine> filterList = new LinkedList<>();
        final List<ProductionLine> list = (List<ProductionLine>) controller.listProductionLines();
        SelectWidget<ProductionLine> selector = null;
        boolean added = false;
        do {
            selector = new SelectWidget<>("Which production lines do you want to filter?", list, new ProductionLinePrinter());
            selector.show();
            added = add(selector.selectedElement(), filterList);
            list.remove(selector.selectedElement());
        } while (added && !list.isEmpty());
        return filterList;
    }

    private <T> boolean add(T item, List<T> toList) {
        if (item != null) {
            return toList.add(item);
        }
        return false;
    }

    @Override
    public String headline() {
        return "Check Errors Notificatoins";
    }

}