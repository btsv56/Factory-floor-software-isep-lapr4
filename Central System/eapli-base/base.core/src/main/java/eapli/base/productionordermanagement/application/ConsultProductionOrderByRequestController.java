package eapli.base.productionordermanagement.application;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.application.Controller;

public class ConsultProductionOrderByRequestController implements Controller {

    final ConsultProductionOrderByRequestService svc= new ConsultProductionOrderByRequestService();

    /**
     * All deposits
     * @return all deposits
     */
    public Iterable<ProductionOrder> byRequest(String requestCode){
        return this.svc.consultByRequest(requestCode);
    }
}
