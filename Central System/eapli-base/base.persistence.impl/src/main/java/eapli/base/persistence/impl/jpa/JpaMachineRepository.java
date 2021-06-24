/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.domain.Protocol;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import javax.persistence.TypedQuery;

/**
 *
 * @author bruno
 */
public class JpaMachineRepository extends BasepaRepositoryBase<Machine,InternalCode,InternalCode> implements MachineRepository {

    public JpaMachineRepository() {
        super("internalCode");
    }
    
    @Override
    public Machine getMachine(Protocol id){
        final TypedQuery<Machine> query = entityManager().createQuery(
                "SELECT m FROM Machine m where m.protocol=:i",Machine.class
        );
        query.setParameter("i", id);
        return query.getSingleResult();
    }
}
