package eapli.base.depositmanagement.domain;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;

/**
 *
 * @author joaol
 */
@Entity
public class DepositMaterialSheet implements AggregateRoot<Integer> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue
    private Integer code;

    //RawMaterial object
    @ManyToOne
    private RawMaterial mat;

    //Amount
    private int amount=-1;

    /**
     * Contructor
     * @param mat: raw material instance
     */
    public DepositMaterialSheet(final RawMaterial mat) {
        Preconditions.noneNull(mat);
        this.mat=mat;
        setAmount(0);
    }

    public DepositMaterialSheet() {
        //For ORM only
    }

    /**
     * Validates and sets amount
     * @param amount
     */
    public void setAmount(final int amount) {
        if (amountMeetsMinimumRequirements(amount)) this.amount = amount;
        else throw new IllegalArgumentException("Amount can't be less than 0.");
    }

    /**
     * Gets amount
     * @return int amount
     */
    public int getAmount() {
        return amount;
    }

    public RawMaterial getMaterial() {
        return mat;
    }

    /**
     * Validates amount
     * @param amount
     * @return True if amount is greater or equal than 0
     */
    private boolean amountMeetsMinimumRequirements(final int amount) {
        return amount>=0;
    }

    /*private int add() {
        if (amountMeetsMinimumRequirements(amount)) amount++;
        else throw new IllegalArgumentException("Amount hasn't been initialized yet.");
        return amount;
    }*/

    /**
     * Checks if two objects are the same
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final DepositMaterialSheet depoSheet = (DepositMaterialSheet) other;
        return this.equals(depoSheet) && code.equals(depoSheet.code) && this.amount==depoSheet.amount;
    }

    /**
     * Returns the identity of this object
     * @return the deposit's code
     */
    @Override
    public Integer identity() {
        return code;
    }

    /**
     * HashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if two objects have the same ID
     * @param o: other object
     * @return True if they have the same ID
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * toString()
     * @return Object's package and ID
     */
    @Override
    public String toString() {
        return "ID: " + code + ", ";
    }
}
