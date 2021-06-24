/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.mps.console;

import eapli.base.app.common.console.BaseApplication;
import eapli.base.app.mps.console.presentation.MainMenu;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BasePasswordPolicy;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;
import eapli.framework.infrastructure.eventpubsub.EventDispatcher;

/**
 *
 * @author bruno
 */
public final class BaseMPS extends BaseApplication {

    private BaseMPS() {

    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        AuthzRegistry.configure(PersistenceContext.repositories().users(), new BasePasswordPolicy(),
                new PlainTextEncoder());
        new BaseMPS().run(args);
    }

    @Override
    protected void doMain(final String[] args) {
        final MainMenu menu = new MainMenu();
        menu.mainLoop();
    }

    @Override
    protected String appTitle() {
        return "Message Processing Service";
    }

    @Override
    protected String appGoodbye() {
        return "Turning off server...";
    }

    @Override
    protected void doSetupEventHandlers(final EventDispatcher dispatcher) {
        // do nothing
    }

}
