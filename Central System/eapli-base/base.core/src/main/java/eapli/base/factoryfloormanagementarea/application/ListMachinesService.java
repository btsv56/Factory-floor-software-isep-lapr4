package eapli.base.factoryfloormanagementarea.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListMachinesService {

    private final AuthorizationService authz= AuthzRegistry.authorizationService();
    private final MachineRepository repo= PersistenceContext.repositories().machine();

    /**
     * All Raw Materials
     *
     * @return all raw materials
     */
    public Iterable<Machine> allMachines() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.FACTORY_FLOOR_MANAGER);
        return this.repo.findAll();
    }
}
