package eapli.base.app.mps.console.presentation.login;

import eapli.base.app.common.console.presentation.authz.LoginUI;
import eapli.base.app.mps.console.presentation.messageprocessingsystem.MessageProcessingServiceMenu;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.actions.Action;

public class LoginAction implements Action {

    @Override
    public boolean execute() {
        boolean login = new LoginUI(BaseRoles.FACTORY_FLOOR_MANAGER).show();
        if (login){
            new MessageProcessingServiceMenu().mainLoop();
        }
        return false;
    }
    
}