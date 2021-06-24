package eapli.base.productionsheetmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;

@Entity
public class ProductionSheetLineProduct implements AggregateRoot<Integer> {

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
    private Product BOMProduct;

    private int quantity;

    protected ProductionSheetLineProduct(){
        // for ORM
    }

    /**
     * Creates a Production Sheet Line
     * @param quantity
     * @param BOMProduct
     */
    public ProductionSheetLineProduct(Product BOMProduct, int quantity){
        Preconditions.nonNull(BOMProduct);
        this.BOMProduct = BOMProduct;
        this.quantity = quantity;
    }


    /**
     * Returns prodyuct
     */
    public Product getProduct(){
        return this.BOMProduct;
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
        return new HashCoder().with(BOMProduct).with(quantity).code();
    }

    @Override
    public boolean sameAs(Object other) {
        final ProductionSheetLineProduct productionSheetLineProduct = (ProductionSheetLineProduct) other;
        return this.equals(productionSheetLineProduct)
                && id.equals(productionSheetLineProduct.id)
                && quantity == productionSheetLineProduct.quantity;
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
        return "eapli.base.productionsheet.domain.ProductionSheet[ item=" + BOMProduct + " quantity " + quantity +"]";
    }

    @Override
    public Integer identity() {
        return this.id;
    }
}
