/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.model.general.Designation;

/**
 *
 * @author Utilizador
 */
public class JpaRawMaterialRepository extends BasepaRepositoryBase<RawMaterial,Designation,Designation> implements RawMaterialRepository {
    
    public JpaRawMaterialRepository() {
        super("internalCode");
    }
    
}
