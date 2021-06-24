/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Bruno Veiga
 */
public class AddRawMaterialCategoryController implements Controller{
    
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final RawMaterialCategoryRepository repository= PersistenceContext.repositories().rawMaterialCategory();
    
    /**
     * Controller do add a raw material category
     * @param name: name of the category
     * @param description: description of the category
     * @return 
     */
    public RawMaterialCategory registerRawMaterialCategory(String name,String description) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER,BaseRoles.PRODUCTION_MANAGER);
        
        final RawMaterialCategory rawMaterialCategory=new RawMaterialCategory(name, description);
        return this.repository.save(rawMaterialCategory);
    }
}
