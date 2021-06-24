package eapli.base.depositmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author joaol
 */
@Entity
public class DepositProductSheet implements AggregateRoot<Integer> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue
    private Integer code;

    //Product object
    @ManyToOne
    private Product prod;

    //Amount
    private int amount=-1;

    /**
     * Contructor
     * @param prod: product instance
     */
    public DepositProductSheet(final Product prod) {
        Preconditions.noneNull(prod);
        this.prod=prod;
        setAmount(0);
    }

    public DepositProductSheet() {
        //For ORM only
    }

    /**
     * Validates and sets amount
     * @param amount
     */
    public void setAmount(final int amount) {
        if (amountMeetsMinimumRequirements(amount)) this.amount=amount;
        else throw new IllegalArgumentException("Amount can't be less than 0.");
    }

    /**
     * Gets amount
     * @return int amount
     */
    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return prod;
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
        final DepositProductSheet depoSheet = (DepositProductSheet) other;
        return this.equals(depoSheet) && this.code.equals(depoSheet.code) && this.amount==depoSheet.amount;
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
        return "eapli.base.depositmanagement.domain.DepositProductSheet[ id=" + code + " ]";
    }
}
