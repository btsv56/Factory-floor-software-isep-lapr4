/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.persistence.impl.jpa;

import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;

/**
 *
 * @author Utilizador
 */
public class JpaRawMaterialCategoryRepository extends BasepaRepositoryBase<RawMaterialCategory,Long,String> implements RawMaterialCategoryRepository {

    public JpaRawMaterialCategoryRepository() {
        super("name");
    }
    
}
