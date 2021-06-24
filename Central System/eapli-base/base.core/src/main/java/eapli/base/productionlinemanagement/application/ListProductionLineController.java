package eapli.base.productionlinemanagement.application;

import eapli.base.productionlinemanagement.domain.ProductionLine;

public class ListProductionLineController {

    final ListProductionLineService svc= new ListProductionLineService();

    /**
     * All production lines
     * @return all production lines
     */
    public Iterable<ProductionLine> allProductionLines(){
        return this.svc.allProductionLines();
    }
}
