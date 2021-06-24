/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.domain;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.model.DomainFactory;

/**
 *
 * @author bruno
 */
public class ProductionResultBuilder implements DomainFactory<ProductionResult> {

    private ProductionResult productionResult;

    public ProductionResultBuilder() {
    }

    public ProductionResultBuilder newProductionResult() {
        productionResult = new ProductionResult();
        return this;
    }

    public ProductionResultBuilder addProductionOrder(ProductionOrder order) {
        productionResult.setProductionOrder(order);
        return this;
    }

    public ProductionResultBuilder addEffectiveTime(Iterable<ProductionTime> time) {
        productionResult.addEffectiveTime(time);
        return this;
    }

    public ProductionResultBuilder addDownTime(Iterable<ProductionTime> time) {
        productionResult.addDownTime(time);
        return this;
    }

    public ProductionResultBuilder addStockIn(Iterable<StockMovement> stock) {
        productionResult.addStockIn(stock);
        return this;
    }

    public ProductionResultBuilder addStockOut(Iterable<StockMovement> stock) {
        productionResult.addStockOut(stock);
        return this;
    }

    public ProductionResultBuilder addFinalProduct(Iterable<FinalProduct> product) {
        productionResult.addFinalProduct(product);
        return this;
    }

    @Override
    public ProductionResult build() {
        if (productionResult.isValid()) {
            return productionResult;
        }
        throw new IllegalStateException("Production Result is not in a consistent state.");
    }

}
