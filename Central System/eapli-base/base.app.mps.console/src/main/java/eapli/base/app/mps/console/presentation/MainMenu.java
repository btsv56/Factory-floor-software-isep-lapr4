package eapli.base.app.mps.console.presentation;

import eapli.base.app.common.console.presentation.console.Exit;
import eapli.base.app.mps.console.presentation.login.LoginAction;
import eapli.framework.actions.menu.Menu;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

public class MainMenu extends AbstractUI {

    // Main Menu
    private static final int LOGIN = 1;
    private static final int EXIT_OPTION = 0;

    @Override
    public boolean show() {
        drawFormTitle();
        return doShow();
    }

    /**
     * @return true if the user selected the exit option
     */
    @Override
    public boolean doShow() {
        final Menu menu = buildMainMenu();
        final MenuRenderer renderer;
        renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        return renderer.render();
    }

    private Menu buildMainMenu() {
        
        final Menu mainMenu = new Menu();

        mainMenu.addItem(LOGIN, "Login", new LoginAction());
        mainMenu.addItem(EXIT_OPTION, "Shut Down", new Exit());
        
        return mainMenu;
    }

    @Override
    public String headline() {
        return "Message Processing System";
    }

}