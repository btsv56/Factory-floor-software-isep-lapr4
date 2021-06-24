/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Utilizador
 */
public class RawMaterialCRUDSmokeTester {
    
     private static final Logger LOGGER = LogManager.getLogger(RawMaterialCRUDSmokeTester.class);
     private final RawMaterialRepository repo= PersistenceContext.repositories().rawMaterial();
     private final RawMaterialCategoryRepository aux= PersistenceContext.repositories().rawMaterialCategory();
     
    public void testRawMaterialCategory() {
        
        //setup categories
         aux.save(new RawMaterialCategory("first","first desc"));
         final RawMaterialCategory cat=aux.ofIdentity("first").orElseThrow(IllegalStateException::new);
        
        //save
       
        repo.save(new RawMaterial(Designation.valueOf("first"),"first desc",cat,new TechnicalSheet("first",".///files/technicalsheets/teste.pdf")));
        repo.save(new RawMaterial(Designation.valueOf("second"),"second desc",cat, new TechnicalSheet("secind",".///files/technicalsheets/teste.pdf")));
        LOGGER.info("»»» created raw materials");
        
        //findAll
        final Iterable<RawMaterial> l= repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all raw materials");
        
        //count
        final long n = repo.count();
        LOGGER.info("»»» # raw materials = {}", n);
        
        //ofIdentity
        final RawMaterial rm1=repo.ofIdentity(Designation.valueOf("first")).orElseThrow(IllegalStateException::new);
        final RawMaterial rm2=repo.ofIdentity(Designation.valueOf("second")).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found raw materials of identity");
        
         // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(rm1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains raw material of identity");
        
        // contains
        final boolean has = repo.contains(rm1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains raw material");
        
        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete raw material");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        aux.deleteOfIdentity("first");
        LOGGER.info("»»» delete raw material of identity");
        
        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # raw materials = {}", n2);
    }
}
