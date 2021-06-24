package eapli.base.productionsheetmanagement.domain;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

@Entity
public class ProductionSheetLineRawMaterial implements AggregateRoot<Integer> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Version
    private long Version;
    @Id
    @GeneratedValue
    private Integer id;
    /**
     * list of items and respective quantities
     */
    @ManyToOne
    private RawMaterial BOMMaterial;

    private int quantity;

    protected ProductionSheetLineRawMaterial(){
        // for ORM
    }

    /**
     * Creates a Production Sheet Line
     * @param quantity
     * @param BOMMaterial
     */
    public ProductionSheetLineRawMaterial(RawMaterial BOMMaterial, int quantity){
        Preconditions.nonNull(BOMMaterial);
        this.BOMMaterial = BOMMaterial;
        this.quantity = quantity;
    }

    /**
     * Returns material
     */
    public RawMaterial getMaterial(){
        return this.BOMMaterial;
    }

    /**
     * Returns quantity
     * @return
     */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Hashes the ProductionSheet object
     *
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return new HashCoder().with(BOMMaterial).with(quantity).code();
    }

    @Override
    public boolean sameAs(Object other) {
        final ProductionSheetLineRawMaterial productionSheetLine = (ProductionSheetLineRawMaterial) other;
        return this.equals(productionSheetLine)
                && id.equals(productionSheetLine.id)
                && quantity == (productionSheetLine.quantity);
    }

    /**
     * Verifies if two objects have the same ID
     *
     * @param o Object to be compared
     * @return true if the objects have the same ID
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("To be implemented");
    }

    /**
     * Description of a Production Sheet
     *
     * @return Production Sheet Description
     */
    @Override
    public String toString() {
        return "eapli.base.productionsheet.domain.ProductionSheet[ item=" + BOMMaterial + " quantity " + quantity +"]";
    }

    @Override
    public Integer identity() {
        return this.id;
    }
}
