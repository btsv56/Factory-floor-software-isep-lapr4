/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.repository;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.domain.Protocol;
import eapli.framework.domain.repositories.DomainRepository;

/**
 *
 * @author bruno
 */
public interface MachineRepository extends DomainRepository<InternalCode, Machine> {
    
    Machine getMachine(Protocol id);
    
}
