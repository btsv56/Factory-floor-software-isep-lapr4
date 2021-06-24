/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.application;

import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.domain.model.general.Description;

/**
 *
 * @author bruno
 */
public class SpecifyMachineController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();

    public Machine specifyMachine(final InternalCode internalCode, final SerialNumber serialNumber, final Description description, final InstallationDate dateOfIntallation, final Brand brand, final Model model, final MachineState machineState, final Protocol protocol) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        Iterable<Machine> machines=machineRepository.findAll();
        for (Machine m : machines) {
            if (serialNumber.equals(m.getSerialNumber()) || protocol.equals(m.getProtocol())) {
                return null;
            }
        }
        final Machine theMachine = new Machine(internalCode, serialNumber, description, dateOfIntallation, brand, model, machineState, protocol);
        final Machine machine = machineRepository.save(theMachine);
        return machine;

    }
}
