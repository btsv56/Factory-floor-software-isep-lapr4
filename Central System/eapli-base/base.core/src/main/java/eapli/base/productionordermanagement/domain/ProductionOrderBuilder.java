package eapli.base.productionordermanagement.domain;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.framework.domain.model.DomainFactory;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.util.Calendars;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ProductionOrderBuilder implements DomainFactory<ProductionOrder> {

    private Designation internalCode;
    private String desc;
    private String state;
    private Designation lot;
    private Designation request;
    private String emissionDate;
    private String predictedExecutionDate;
    private int quantityToProduce;
    private Designation prodSheet=null;
    private ProductionSheet prodSheetInst=null;

    public ProductionOrderBuilder gatherVariablesWithoutSheet(final Designation internalCode, final String desc, final String state, final Designation lot,
                                                         final Designation request, final String emissionDate, final String predictedExecutionDate,
                                                         final int quantityToProduce, final Designation prodSheet) {
        gatherVariables(internalCode, desc, state, lot, request, emissionDate, predictedExecutionDate, quantityToProduce);
        this.prodSheet = prodSheet;
        return this;
    }

    public ProductionOrderBuilder gatherVariablesWithSheet(final Designation internalCode, final String desc, final String state, final Designation lot,
                                                  final Designation request, final String emissionDate, final String predictedExecutionDate,
                                                  final int quantityToProduce, final ProductionSheet prodSheet) {
        gatherVariables(internalCode, desc, state, lot, request, emissionDate, predictedExecutionDate, quantityToProduce);
        this.prodSheetInst = prodSheet;
        return this;
    }

    private void gatherVariables(final Designation internalCode, final String desc, final String state, final Designation lot,
                                 final Designation request, final String emissionDate, final String predictedExecutionDate,
                                 final int quantityToProduce) {
        this.internalCode = internalCode;
        this.desc = desc;
        this.state = state;
        this.lot = lot;
        this.request = request;
        this.emissionDate = emissionDate;
        this.predictedExecutionDate = predictedExecutionDate;
        this.quantityToProduce = quantityToProduce;
    }

    @Override
    public ProductionOrder build() {
        ProductionOrder prodOrder = new ProductionOrder(this.internalCode, this.desc);
        prodOrder.setState(state);
        prodOrder.setLot(new Lot(lot));
        prodOrder.setRequest(new Request(request));
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        try {
            prodOrder.setEmissionDate(Calendars.calendarFromDate(sdf.parse(emissionDate)));
            prodOrder.setPredictedExecutionDate(Calendars.calendarFromDate(sdf.parse(predictedExecutionDate)));
        } catch (ParseException e) {
            return null;
        }
        prodOrder.setQuantityToProduce(quantityToProduce);
        if (prodSheetInst==null) {
            try {
                ProductionSheetRepository psr = PersistenceContext.repositories().productionSheets();
                prodSheetInst = psr.ofIdentity(prodSheet).orElse(null);
                if (prodSheetInst == null) {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
        prodOrder.setProductionSheet(prodSheetInst);
        return prodOrder;
    }
}
