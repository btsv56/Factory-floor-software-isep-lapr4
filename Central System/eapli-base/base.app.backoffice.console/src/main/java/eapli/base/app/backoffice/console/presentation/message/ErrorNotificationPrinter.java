package eapli.base.app.backoffice.console.presentation.message;

import eapli.base.errornotificationmanagement.domain.ErrorNotification;
import eapli.framework.visitor.Visitor;

public class ErrorNotificationPrinter implements Visitor<ErrorNotification> {
    @Override
    public void visit(ErrorNotification visitee) {
        System.out.printf("%s", visitee.identity());
    }

}
