/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.application;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;

/**
 *
 * @author bruno
 */
public class ListProductionLineService {
    
    private final ProductionLineRepository repo= PersistenceContext.repositories().productionLine();
    
    public Iterable<ProductionLine> allProductionLines() {
        return this.repo.findAll();
    }
    
}
