package eapli.base.app.mps.console.presentation.messageprocessingsystem;

import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions.ActivateMessageProcessingAction;
import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions.ChangeMessageProcessingStateAction;
import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions.ScheduleMessageProcessingAction;
import eapli.base.app.mps.console.presentation.messageprocessingsystem.presentation.actions.SeeMessageProcessingStateAction;
import eapli.framework.actions.menu.Menu;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

public class MessageProcessingServiceMenu extends AbstractUI {

    private static final int EXIT_OPTION = 0;

    // MESSAGE_PROCESSING_SYSTEM
    private static final int SCHEDULE_MESSAGE_PROCESSING = 1;
    private static final int SEE_MESSAGE_PROCESSING_STATE = 2;
    private static final int CHANGE_MESSAGE_PROCESSING_STATE = 3;
    private static final int ACTIVATE_AUTOMATIC_PROCESSING = 4;

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

    @Override
    protected boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);

        return renderer.render();
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        mainMenu.addItem(SCHEDULE_MESSAGE_PROCESSING, "Schedule Message Processing", new ScheduleMessageProcessingAction());
        mainMenu.addItem(SEE_MESSAGE_PROCESSING_STATE, "See Production Line states", new SeeMessageProcessingStateAction());
        mainMenu.addItem(CHANGE_MESSAGE_PROCESSING_STATE, "Change Production Lines' state", new ChangeMessageProcessingStateAction());
        mainMenu.addItem(ACTIVATE_AUTOMATIC_PROCESSING, "Activate or change automatic messages processing", new ActivateMessageProcessingAction());

        mainMenu.addItem(EXIT_OPTION, "Log out", new ExitWithMessageAction());
        return mainMenu;
    }

    @Override
    public String headline() {
        return authz.session().map(s -> "Message Processing System [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Message Processing System [ ==Anonymous== ]");
    }

}