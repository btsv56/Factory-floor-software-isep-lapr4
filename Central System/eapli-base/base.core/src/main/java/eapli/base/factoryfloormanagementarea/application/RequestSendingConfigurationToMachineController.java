/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.application;

import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public class RequestSendingConfigurationToMachineController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MachineRepository repository = PersistenceContext.repositories().machine();

    public Iterable<Machine> getAllMachines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return repository.findAll();
    }

    public boolean sendConfigurationFile(Machine machine, ConfigurationFile configFile) throws IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        return new SendConfigurationsTelnet().sendConfigurationFile(machine, configFile);
    }

}
