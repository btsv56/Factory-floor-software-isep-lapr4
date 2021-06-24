/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.uis;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.printers.ProductionOrderPrinter;
import eapli.base.app.mps.console.utils.ConsoleUtility;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionresultmanagement.application.MessageProcessingServiceController;
import eapli.base.utils.Time;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;

/**
 *
 * @author bruno
 */
public class ScheduleMessageProcessingUI extends AbstractUI {

    private final MessageProcessingServiceController controller = new MessageProcessingServiceController();

    @Override
    protected boolean doShow() {

        final SelectWidget<ProductionOrder> selector = new SelectWidget<>(
                "Wich production order do you want to (re)process?", controller.listProductionOrders(), new ProductionOrderPrinter());
        selector.show();
        final ProductionOrder order = selector.selectedElement();
        if (order != null) {
            final Time startTime = ConsoleUtility.readHourOfDay("What time do you want to schedule? [hh:mm] (empty for now) ");
            final int interval = ConsoleUtility.readMinutes("For how long? (in minutes, empty for 15 mins) ");

            try {
                controller.reprocessMessagesAtGivenTime(startTime, interval, order);
                System.out.println("Operation scheduled successfully.");
            } catch (final Exception e) {
                System.out.println("An unexpected error ocured.");
                return true;
            }
        }

        return false;
    }

    @Override
    public String headline() {
        return "(Re)Process messages at a given time";
    }

}
