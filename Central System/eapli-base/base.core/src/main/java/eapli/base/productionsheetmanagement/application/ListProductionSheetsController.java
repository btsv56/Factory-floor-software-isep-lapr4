package eapli.base.productionsheetmanagement.application;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;

public class ListProductionSheetsController {

    final ListProductionSheetsService svc= new ListProductionSheetsService();

    /**
     * All production orders
     * @return all production orders
     */
    public Iterable<ProductionSheet> productionSheets(){
        return this.svc.allProductionSheets();
    }
}
