/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionordermanagement.application;

import eapli.base.productionordermanagement.domain.ProductionOrder;

/**
 *
 * @author Utilizador
 */
public class ListProductionOrdersPerStateController {

    final ListProductionOrdersPerStateService svc = new ListProductionOrdersPerStateService();

    /**
     * All production orders in a state
     *
     * @return all production orders in a state
     */
    public Iterable<ProductionOrder> productionOrdersPerState(String state) {
        return this.svc.productionOrdersPerState(state);
    }
}
