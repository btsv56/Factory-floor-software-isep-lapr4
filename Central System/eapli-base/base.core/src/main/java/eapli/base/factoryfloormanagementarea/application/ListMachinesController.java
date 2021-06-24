package eapli.base.factoryfloormanagementarea.application;

import eapli.base.factoryfloormanagementarea.domain.Machine;

public class ListMachinesController {

    final ListMachinesService svc= new ListMachinesService();

    /**
     * All raw materials
     * @return all raw materials
     */
    public Iterable<Machine> allMachines(){
        return this.svc.allMachines();
    }
}
