package eapli.base.productionsheetmanagement.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Preconditions;

@Entity
public class ProductionSheet implements AggregateRoot<Designation> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Version
    private Long Version;

    /**
     * ID of the production Sheet
     */
    @Id
    private Designation productionSheetID ;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<ProductionSheetLineProduct> BOMItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    private List<ProductionSheetLineRawMaterial> BOMMaterials = new ArrayList<>();

    /**
     *
     * @param productionSheetID
     * @param BOMItems
     */
    public ProductionSheet(final Designation productionSheetID, List<ProductionSheetLineProduct> BOMItems, List<ProductionSheetLineRawMaterial> BOMMaterials){
        addProductionSheetID(productionSheetID);
        addProductionSheetLines(BOMItems);
        addProductionSheetLinesMaterials(BOMMaterials);
    }

    protected ProductionSheet() {
        //For ORM
    }

    /**
     * adds the production sheet id
     * @param BOMItems
     */
    private void addProductionSheetLines(List<ProductionSheetLineProduct> BOMItems){
        this.BOMItems = BOMItems;
    }

    /**
     * Makes sure the production sheet id meets the business requirements
     * @param BOMItems
     */
    private void addProductionSheetLinesMaterials(List<ProductionSheetLineRawMaterial> BOMItems){
        if(!BOMItems.isEmpty()){
            this.BOMMaterials = BOMItems;
        } else {
            throw new IllegalArgumentException("You must specify at least one Raw Material");
        }
    }

    /**
     * adds the production sheet id
     * @param productionSheetID
     * @return
     */
    private void addProductionSheetID(Designation productionSheetID) {
        if(ensureProductionSheetIDMeetsBusinessRequirements(productionSheetID)){
            this.productionSheetID = productionSheetID;
        } else {
            throw new IllegalArgumentException("Invalid Production Sheet ID");
        }
    }

    /**
     * Makes sure the production sheet id meets the business requirements
     * @param productionSheetID
     * @return
     */
    private boolean ensureProductionSheetIDMeetsBusinessRequirements(Designation productionSheetID) {
        Preconditions.nonNull(productionSheetID);
        return productionSheetID.toString().length() <= 15 ;
    }

    /**
     * Verifies if two instances of an object
     * is exactly the same
     *
     * @param other object to be compared
     * @return true if everything is the same
     */
    @Override
    public boolean sameAs(Object other) {
        final ProductionSheet productionSheet = (ProductionSheet) other;
        return this.equals(productionSheet)
                && this.productionSheetID.equals(productionSheet.productionSheetID);
    }

    /**
     * Returns the identity of a Production Sheet
     *
     * @return identity of the object
     */
    @Override
    public Designation identity() {
        return this.productionSheetID;
    }

    public List<ProductionSheetLineProduct> getBOMItems(){
        return this.BOMItems;
    }

    public List<ProductionSheetLineRawMaterial> getBOMMaterials(){
        return this.BOMMaterials;
    }
    /**
     * Hashes the ProductionSheet object
     *
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Verifies if two objects have the same ID
     *
     * @param o Object to be compared
     * @return true if the objects have the same ID
     */
    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Description of a Production Sheet
     *
     * @return Production Sheet Description
     */
    /**
     * toString()
     * @return Object's package and ID
     */
    @Override
    public String toString() {
        StringBuilder out= new StringBuilder("Production Sheet ID=" + productionSheetID);
        if (!BOMMaterials.isEmpty()) {
            out.append("\nMaterials:\n");
            for (ProductionSheetLineRawMaterial mat : BOMMaterials) {
                out.append("    -").append(mat.getMaterial().description()).append("\n    Amount: ").append(mat.getQuantity()).append("\n");
            }
        }
        if (!BOMItems.isEmpty()) {
            out.append("\nProducts:\n");
            for (ProductionSheetLineProduct prod : BOMItems) {
                out.append("    -").append(prod.getProduct().description()).append("\n    Amount: ").append(prod.getQuantity()).append("\n");
            }
        }
        return out.toString();
    }
}
