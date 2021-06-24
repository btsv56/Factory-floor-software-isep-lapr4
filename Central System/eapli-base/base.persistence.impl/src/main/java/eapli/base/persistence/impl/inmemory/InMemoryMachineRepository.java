/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.domain.Protocol;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author bruno
 */
public class InMemoryMachineRepository extends InMemoryDomainRepository<InternalCode, Machine> implements MachineRepository{
    static{
        InMemoryInitializer.init();
    }
    
    @Override
    public Machine getMachine(Protocol id){
        return matchOne(e->e.hasId(id.getInt())).get();
    }
}
