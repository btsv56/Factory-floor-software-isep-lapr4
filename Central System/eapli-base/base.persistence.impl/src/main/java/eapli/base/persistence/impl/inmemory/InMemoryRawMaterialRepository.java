/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author Utilizador
 */
public class InMemoryRawMaterialRepository extends InMemoryDomainRepository<Designation, RawMaterial> implements RawMaterialRepository{
    
    static{
        InMemoryInitializer.init();
    }
}
