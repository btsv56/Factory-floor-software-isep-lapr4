/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.infrastructure.smoketests.backoffice;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.framework.validations.Invariants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Utilizador
 */
public class RawMaterialCategoryCRUDSmokeTester {

     private static final Logger LOGGER = LogManager.getLogger(RawMaterialCategoryCRUDSmokeTester.class);
     private final RawMaterialCategoryRepository repo= PersistenceContext.repositories().rawMaterialCategory();
    
    public void testRawMaterialCategory() {
        //save
        repo.save(new RawMaterialCategory("first","first desc"));
        repo.save(new RawMaterialCategory("second","second desc"));
        LOGGER.info("»»» created raw material categories");
        
        //findAll
        final Iterable<RawMaterialCategory> l= repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all raw material categories");
        
        //count
        final long n = repo.count();
        LOGGER.info("»»» # raw material categories = {}", n);
        
        //ofIdentity
        final RawMaterialCategory rm1=repo.ofIdentity("first").orElseThrow(IllegalStateException::new);
        final RawMaterialCategory rm2=repo.ofIdentity("second").orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found raw material categories of identity");
        
         // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(rm1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains raw material category of identity");
        
        // contains
        final boolean has = repo.contains(rm1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains raw material category");
        
        // delete
        repo.delete(rm1);
        LOGGER.info("»»» delete raw material category");

        // deleteOfIdentity
        repo.deleteOfIdentity(rm2.identity());
        LOGGER.info("»»» delete raw material category of identity");
        
        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # raw material categories = {}", n2);
    }
    
}
