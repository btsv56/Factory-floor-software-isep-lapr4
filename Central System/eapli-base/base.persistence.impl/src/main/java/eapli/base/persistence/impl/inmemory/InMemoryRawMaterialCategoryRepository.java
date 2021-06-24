/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.inmemory;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

/**
 *
 * @author Utilizador
 */
public class InMemoryRawMaterialCategoryRepository extends InMemoryDomainRepository<String, RawMaterialCategory> implements RawMaterialCategoryRepository{
    
    static{
        InMemoryInitializer.init();
    }
    
}
