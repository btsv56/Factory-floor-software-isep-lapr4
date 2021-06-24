/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

/**
 *
 * @author Bruno Veiga
 */
public class AddRawMaterialToCatalogueController implements Controller {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final ListRawMaterialCategoryService rmcs= new ListRawMaterialCategoryService();
    private final RawMaterialRepository repository=  PersistenceContext.repositories().rawMaterial();
    
    /**
     * Controller to add raw material to catalogue
     * 
     * @param internalCode: internal code of the raw material
     * @param description: description of the raw material
     * @param rawMaterialCategory: category of the raw material
     * @param nameTechnicalSheet: name of the technical sheet of the raw material
     * @param pathTechnicalSheet: path to the technical sheet
     * @return raw material
     */
    public RawMaterial addRawMaterialToCatalogue(String internalCode,String description, RawMaterialCategory rawMaterialCategory,
            String nameTechnicalSheet,String pathTechnicalSheet) {
        
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        
        final RawMaterial rawMaterial= new RawMaterial(Designation.valueOf(internalCode),description,
                rawMaterialCategory,new TechnicalSheet(nameTechnicalSheet,pathTechnicalSheet));
        
        return this.repository.save(rawMaterial);
    }
    
    
    /**
     * Gets all raw material categories
     * @return raw material categories
     */
    public Iterable<RawMaterialCategory> getRawMaterialCategories() {
        return this.rmcs.allRawMaterialCategories();
    }
}
