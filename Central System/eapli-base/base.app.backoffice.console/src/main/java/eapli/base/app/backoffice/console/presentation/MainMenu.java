/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.app.backoffice.console.presentation;

import eapli.base.Application;
import eapli.base.app.backoffice.console.presentation.authz.AddUserUI;
import eapli.base.app.backoffice.console.presentation.authz.DeactivateUserAction;
import eapli.base.app.backoffice.console.presentation.authz.ListUsersAction;
import eapli.base.app.backoffice.console.presentation.clientuser.AcceptRefuseSignupRequestAction;
import eapli.base.app.backoffice.console.presentation.deposit.ListDepositAction;
import eapli.base.app.backoffice.console.presentation.deposit.SpecifyNewDepositAction;
import eapli.base.app.backoffice.console.presentation.errornotificationmanagement.ArchiveErrorNotificationAction;
import eapli.base.app.backoffice.console.presentation.errornotificationmanagement.CheckErrorNotificationAction;
import eapli.base.app.backoffice.console.presentation.export.ExportInformationAction;
import eapli.base.app.backoffice.console.presentation.machine.AttachConfigurationFileToMachineAction;
import eapli.base.app.backoffice.console.presentation.machine.RequestSendingConfigurationToMachineAction;
import eapli.base.app.backoffice.console.presentation.machine.SpecifyMachineAction;
import eapli.base.app.backoffice.console.presentation.message.CheckArchivedErrorNotificationsAction;
import eapli.base.app.backoffice.console.presentation.product.AddProductToCatalogueAction;
import eapli.base.app.backoffice.console.presentation.product.CheckProductsWithoutProductionSheetAction;
import eapli.base.app.backoffice.console.presentation.product.ImportProductsAction;
import eapli.base.app.backoffice.console.presentation.product.ListProductsAction;
import eapli.base.app.backoffice.console.presentation.productionline.ListProductionLineAction;
import eapli.base.app.backoffice.console.presentation.productionline.SpecifyNewProductionLineAction;
import eapli.base.app.backoffice.console.presentation.productionorder.*;
import eapli.base.app.backoffice.console.presentation.productionsheet.ListProductionSheetsAction;
import eapli.base.app.backoffice.console.presentation.productionsheet.SpecifyProductionSheetAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.AddRawMaterialCategoryAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.AddRawMaterialToCatalogueAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.ListRawMaterialCategoryAction;
import eapli.base.app.backoffice.console.presentation.rawmaterial.ListRawMaterialsAction;
import eapli.base.app.common.console.presentation.authz.MyUserMenu;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Actions;
import eapli.framework.actions.menu.Menu;
import eapli.framework.actions.menu.MenuItem;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.ExitWithMessageAction;
import eapli.framework.presentation.console.menu.HorizontalMenuRenderer;
import eapli.framework.presentation.console.menu.MenuItemRenderer;
import eapli.framework.presentation.console.menu.MenuRenderer;
import eapli.framework.presentation.console.menu.VerticalMenuRenderer;

/**
 *
 * @author Paulo Gandra Sousa
 */
public class MainMenu extends AbstractUI {

    private static final String RETURN_LABEL = "Return ";

    private static final int EXIT_OPTION = 0;

    // USERS
    private static final int ADD_USER_OPTION = 1;
    private static final int LIST_USERS_OPTION = 2;
    private static final int DEACTIVATE_USER_OPTION = 3;
    private static final int ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION = 4;

    // SETTINGS
    // RAW MATERIAL CATEGORIES
    private static final int RAW_MATERIAL_CATEGORY_REGISTER_OPTION = 1;
    private static final int RAW_MATERIAL_CATEGORY_LIST_OPTION = 2;

    // RAW MATERIALS
    private static final int RAW_MATERIAL_REGISTER_OPTION = 3;
    private static final int RAW_MATERIAL_LIST_OPTION = 4;

    // MACHINE
    private static final int SPECIFY_MACHINE_OPTION = 1;
    private static final int ATTACH_CONFIGURATION_FILE_TO_MACHINE = 2;
    private static final int CONFIG_MACHINE = 3;

    //PRODUCTS
    private static final int ADD_PRODUCT_OPTION = 1;
    private static final int PRODUCTION_SHEET_SPECIFICATION_OPTION = 2;
    private static final int IMPORT_PRODUCTS_OPTION = 3;
    private static final int CHECK_PRODUCTS_PRODUCTION_SHEET = 4;
    private static final int LIST_PRODUCTS = 5;
    private static final int LIST_PRODUCTION_SHEETS = 6;

    // DEPOSITS
    private static final int DEPOSIT_REGISTER_OPTION = 1;
    private static final int DEPOSIT_LIST_OPTION = 2;

    //PRODUCTION LINES
    private static final int PRODUCTION_LINE_REGISTER = 1;
    private static final int PRODUCTION_LINE_LIST = 2;

    //PRODUCTION ORDERS
    private static final int ADD_PRODUCTION_ORDER = 1;
    private static final int IMPORT_ORDERS_OPTION = 2;
    private static final int LIST_PRODUCTION_ORDERS = 3;
    private static final int CHANGE_ORDER_STATE = 4;
    private static final int CONSULT_ORDERS_BY_REQUEST = 5;

    //ERROR NOTIFICATIONS
    private static final int CONSULT_ERROR_MESSAGES = 1;
    private static final int ARCHIVE_ERROR_MESSAGE = 2;
    private static final int CHECK_ERROR_NOTIFICATIONS = 3;

    // MAIN MENU
    private static final int MY_USER_OPTION = 1;
    private static final int USERS_OPTION = 2;
    private static final int SETTINGS_OPTION = 3;
    private static final int RAW_MATERIAL_OPTION = 4;
    private static final int PRODUCT_OPTION = 5;
    private static final int DEPOSIT_OPTION = 6;
    private static final int MACHINE_OPTION = 7;
    private static final int PRODUCTION_LINE_OPTION = 8;
    private static final int PRODUCTION_ORDER_OPTION = 9;
    private static final int ERROR_NOTIFICATIONS = 10;
    private static final int EXPORT = 11;

    private static final String SEPARATOR_LABEL = "--------------";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();

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
        if (Application.settings().isMenuLayoutHorizontal()) {
            renderer = new HorizontalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        } else {
            renderer = new VerticalMenuRenderer(menu, MenuItemRenderer.DEFAULT);
        }
        return renderer.render();
    }

    @Override
    public String headline() {

        return authz.session().map(s -> "Base [ @" + s.authenticatedUser().identity() + " ]")
                .orElse("Base [ ==Anonymous== ]");
    }

    private Menu buildMainMenu() {
        final Menu mainMenu = new Menu();

        final Menu myUserMenu = new MyUserMenu();
        mainMenu.addSubMenu(MY_USER_OPTION, myUserMenu);

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.ADMIN)) {
            final Menu usersMenu = buildUsersMenu();
            mainMenu.addSubMenu(USERS_OPTION, usersMenu);
            final Menu settingsMenu = buildAdminSettingsMenu();
            mainMenu.addSubMenu(SETTINGS_OPTION, settingsMenu);
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER)) {
            final Menu rawMaterialMenu = buildRawMaterialCategoryMenu();
            mainMenu.addSubMenu(RAW_MATERIAL_OPTION, rawMaterialMenu);
            final Menu productsMenu = buildProductMenu();
            mainMenu.addSubMenu(PRODUCT_OPTION, productsMenu);
            final Menu productionOrdersMenu = buildProductionOrdersMenu();
            mainMenu.addSubMenu(PRODUCTION_ORDER_OPTION, productionOrdersMenu);
            mainMenu.addItem(EXPORT, "Export information", new ExportInformationAction());
        }

        if (authz.isAuthenticatedUserAuthorizedTo(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER)) {
            final Menu depositMenu = buildDepositMenu();
            mainMenu.addSubMenu(DEPOSIT_OPTION, depositMenu);
            final Menu machineMenu = buildMachineMenu();
            mainMenu.addSubMenu(MACHINE_OPTION, machineMenu);
            final Menu pLineMenu = buildProductionLineMenu();
            mainMenu.addSubMenu(PRODUCTION_LINE_OPTION, pLineMenu);
            final Menu errorNotificationMenu = buildErrorNotificationMenu();
            mainMenu.addSubMenu(ERROR_NOTIFICATIONS, errorNotificationMenu);
        }

        if (!Application.settings().isMenuLayoutHorizontal()) {
            mainMenu.addItem(MenuItem.separator(SEPARATOR_LABEL));
        }

        mainMenu.addItem(EXIT_OPTION, "Exit", new ExitWithMessageAction());

        return mainMenu;
    }

    private Menu buildErrorNotificationMenu() {
        final Menu menu = new Menu("Error Notifications >");
        menu.addItem(CONSULT_ERROR_MESSAGES, "Consult Archived Error Messages", new CheckArchivedErrorNotificationsAction());
        menu.addItem(ARCHIVE_ERROR_MESSAGE, "Archive error message", new ArchiveErrorNotificationAction());
        menu.addItem(CHECK_ERROR_NOTIFICATIONS, "Check Error Notifications", new CheckErrorNotificationAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildAdminSettingsMenu() {
        final Menu menu = new Menu("Settings >");
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildUsersMenu() {
        final Menu menu = new Menu("Users >");

        menu.addItem(ADD_USER_OPTION, "Add User", new AddUserUI()::show);
        menu.addItem(LIST_USERS_OPTION, "List all Users", new ListUsersAction());
        menu.addItem(DEACTIVATE_USER_OPTION, "Deactivate User", new DeactivateUserAction());
        menu.addItem(ACCEPT_REFUSE_SIGNUP_REQUEST_OPTION, "Accept/Refuse Signup Request",
                new AcceptRefuseSignupRequestAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildRawMaterialCategoryMenu() {
        final Menu menu = new Menu("Raw Materials >");
        //Raw Material Categories
        menu.addItem(RAW_MATERIAL_CATEGORY_REGISTER_OPTION, "Add new raw material category", new AddRawMaterialCategoryAction());
        menu.addItem(RAW_MATERIAL_CATEGORY_LIST_OPTION, "List raw material categories", new ListRawMaterialCategoryAction());

        //Raw Material
        menu.addItem(RAW_MATERIAL_REGISTER_OPTION, "Add new raw material to catalogue", new AddRawMaterialToCatalogueAction());
        menu.addItem(RAW_MATERIAL_LIST_OPTION, "List raw materials from catalogue", new ListRawMaterialsAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildDepositMenu() {
        final Menu menu = new Menu("Deposits >");
        menu.addItem(DEPOSIT_REGISTER_OPTION, "Create new deposit", new SpecifyNewDepositAction());
        menu.addItem(DEPOSIT_LIST_OPTION, "List deposits", new ListDepositAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildProductMenu() {
        final Menu menu = new Menu("Products >");
        menu.addItem(ADD_PRODUCT_OPTION, "Add a New Product", new AddProductToCatalogueAction());
        menu.addItem(PRODUCTION_SHEET_SPECIFICATION_OPTION, "Specify a Production Sheet", new SpecifyProductionSheetAction());
        menu.addItem(IMPORT_PRODUCTS_OPTION, "Import Products Via File", new ImportProductsAction());
        menu.addItem(CHECK_PRODUCTS_PRODUCTION_SHEET, "Check Products Without Production Sheet", new CheckProductsWithoutProductionSheetAction());
        menu.addItem(LIST_PRODUCTS, "List All Products", new ListProductsAction());
        menu.addItem(LIST_PRODUCTION_SHEETS, "List All Production Sheets", new ListProductionSheetsAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildMachineMenu() {
        final Menu menu = new Menu("Machine >");

        menu.addItem(SPECIFY_MACHINE_OPTION, "Specify Machine", new SpecifyMachineAction());
        menu.addItem(ATTACH_CONFIGURATION_FILE_TO_MACHINE, "Attach configuration file to machine", new AttachConfigurationFileToMachineAction());
        menu.addItem(CONFIG_MACHINE, "Send configuration to a machine", new RequestSendingConfigurationToMachineAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);

        return menu;
    }

    private Menu buildProductionLineMenu() {
        final Menu menu = new Menu("Production Lines >");
        menu.addItem(PRODUCTION_LINE_REGISTER, "Create new production lines", new SpecifyNewProductionLineAction());
        menu.addItem(PRODUCTION_LINE_LIST, "List production lines", new ListProductionLineAction());
        menu.addItem(EXIT_OPTION, RETURN_LABEL, Actions.SUCCESS);
        return menu;
    }

    private Menu buildProductionOrdersMenu() {
        final Menu menu = new Menu("Production Orders >");
        menu.addItem(ADD_PRODUCTION_ORDER, "Add Production Order", new SpecifyNewProductionOrderAction());
        menu.addItem(IMPORT_ORDERS_OPTION, "Import Production Orders", new ImportProductionOrdersAction());
        menu.addItem(LIST_PRODUCTION_ORDERS, "List Production Orders", new ListProductionOrdersPerStateAction());
        menu.addItem(CHANGE_ORDER_STATE, "Change a Production Order's State", new ChangeProductionOrderStateAction());
        menu.addItem(CONSULT_ORDERS_BY_REQUEST, "Consult Production Orders by Request", new ConsultProductionOrderByRequestAction());
        return menu;
    }
}
