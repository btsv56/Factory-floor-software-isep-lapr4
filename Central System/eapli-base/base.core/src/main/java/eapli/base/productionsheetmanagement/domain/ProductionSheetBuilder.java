package eapli.base.productionsheetmanagement.domain;

import eapli.framework.domain.model.general.Designation;

import java.util.ArrayList;
import java.util.List;

public class ProductionSheetBuilder {

    private String productionSheetID;
    private List<ProductionSheetLineProduct> BOMItems = new ArrayList<>();
    private List<ProductionSheetLineRawMaterial> BOMMats = new ArrayList<>();


    /**
     * Adds the reference to the Production sheet Line
     * @param productionSheetLineProduct
     */
    public void addProductionSheetLine(ProductionSheetLineProduct productionSheetLineProduct){
        this.BOMItems.add(productionSheetLineProduct);
    }

    /**
     * Adds the reference to the Production sheet Line
     * @param productionSheetLineRawMaterial
     */
    public void addProductionSheetMaterial(ProductionSheetLineRawMaterial productionSheetLineRawMaterial){
        this.BOMMats.add(productionSheetLineRawMaterial);
    }

    /**
     * Adds the production Sheet Id entered by the user
     * @param productionSheetID
     */
    public void addProductionSheetID(String productionSheetID) {
        this.productionSheetID = productionSheetID;
    }

    /**
     * Builds the Object
     * @return
     */
    public ProductionSheet build(){
        return new ProductionSheet(Designation.valueOf(productionSheetID), BOMItems, BOMMats);
    }
}
