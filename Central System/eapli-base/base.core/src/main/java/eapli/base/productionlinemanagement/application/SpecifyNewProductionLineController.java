package eapli.base.productionlinemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.factoryfloormanagementarea.application.ListMachinesService;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class SpecifyNewProductionLineController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ProductionLineRepository pLineRepository = PersistenceContext.repositories().productionLine();
    private final ListMachinesService listMachines = new ListMachinesService();
    private ProductionLine pLine;

    public void specifyNewProductionLine(final Designation code, final String desc) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        pLine = new ProductionLine(code, desc);
    }

    public Iterable<Machine> getMachines() {
        return listMachines.allMachines();
    }

    public boolean addMachine(Machine machine) {
        return pLine.addMachine(machine);
    }

    public ProductionLine save() {
        return this.pLineRepository.save(pLine);
    }
}
