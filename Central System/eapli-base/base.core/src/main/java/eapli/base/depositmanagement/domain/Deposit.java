package eapli.base.depositmanagement.domain;

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
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author joaol
 */
@Entity
public class Deposit implements AggregateRoot<Designation> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    private Designation internalCode;

    //Description
    private String desc;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<DepositMaterialSheet> mats = new ArrayList<>();

    @OneToMany(cascade = CascadeType.MERGE)
    private List<DepositProductSheet> prods = new ArrayList<>();

    /**
     * Deposit contructor
     * @param code: identification code
     * @param desc: description
     */
    public Deposit(final Designation code, final String desc) {
        Preconditions.noneNull(code, desc);
        this.internalCode=code;
        setDesc(desc);
    }

    protected Deposit() {
        //for ORM only
    }

    /**
     * Validates and sets description
     * @param desc
     */
    public void setDesc(final String desc) {
        if (descriptionMeetsMinimumRequirements(desc)) this.desc = desc;
        else throw new IllegalArgumentException("Invalid description");
    }

    public Designation getCode() {
        return this.internalCode;
    }

    public String getDescription() {
        return this.desc;
    }

    public List<DepositMaterialSheet> getMaterialSheets() {
        return this.mats;
    }

    public List<DepositProductSheet> getProductSheets() {
        return this.prods;
    }

    /**
     * Adds a Raw Material to the list
     * @param matSheet
     * @return
     */
    public boolean addMaterial(final DepositMaterialSheet matSheet) {
        for (DepositMaterialSheet mat : mats) {
            if (mat.getMaterial().equals(matSheet.getMaterial())) {
                return false;
            }
        }
        return mats.add(matSheet);
    }

    /**
     * Adds a Product to the list
     * @param prodSheet
     * @return
     */
    public boolean addProduct(final DepositProductSheet prodSheet) {
        for (DepositProductSheet prod : prods) {
            if (prod.getProduct().equals(prodSheet.getProduct())) {
                return false;
            }
        }
        return prods.add(prodSheet);
    }

    /**
     * Checks if description is null or empty
     * @param description
     * @return True if it's not null or empty
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    /**
     * Checks if two objects are the same
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final Deposit deposit=(Deposit) other;
        return this.equals(deposit) && this.desc.equals(deposit.desc);
    }

    /**
     * Returns the identity of this object
     * @return the deposit's code
     */
    @Override
    public Designation identity() {
        return this.internalCode;
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
        StringBuilder out= new StringBuilder("Deposit ID=" + internalCode);
        if (!mats.isEmpty()) {
            out.append("\nMaterials:\n");
            for (DepositMaterialSheet mat : mats) {
                out.append("    -").append(mat.getMaterial().description()).append("\n    Amount: ").append(mat.getAmount()).append("\n");
            }
        }
        if (!prods.isEmpty()) {
            out.append("\nProducts:\n");
            for (DepositProductSheet prod : prods) {
                out.append("    -").append(prod.getProduct().description()).append("\n    Amount: ").append(prod.getAmount()).append("\n");
            }
        }
        return out.toString();
    }

}
