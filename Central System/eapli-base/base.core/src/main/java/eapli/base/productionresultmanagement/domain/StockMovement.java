package eapli.base.productionresultmanagement.domain;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import eapli.base.productmanagement.domain.Product;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class StockMovement implements ValueObject {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Version
    private long version;

    //Primary key
    @Id
    @GeneratedValue
    private int id;
    
    private int quantity;
    @OneToOne
    private final Machine machine;
    @ManyToOne
    @JoinColumn(nullable=true)
    private final Deposit deposit;
    @ManyToOne
    @JoinColumn(nullable=true)
    private RawMaterial rawMaterial;
    @ManyToOne
    @JoinColumn(nullable=true)
    private Product product;    

    protected StockMovement() {
        this.machine = null;
        this.deposit = null;
        this.rawMaterial = null;
        this.product = null;
    }

    public StockMovement(final int quantity, final Machine machine, final Deposit deposit,
            final RawMaterial rawMaterial) {
        Preconditions.noneNull(quantity, machine, deposit, rawMaterial);
        setQuantity(quantity);
        this.machine = machine;
        this.deposit = deposit;
        this.rawMaterial = rawMaterial;
    }

    public StockMovement(final int quantity, final Machine machine, final Deposit deposit, final Product product) {
        Preconditions.noneNull(quantity, machine, deposit, product);
        setQuantity(quantity);
        this.machine = machine;
        this.deposit = deposit;
        this.product = product;
    }

    public StockMovement(final int quantity, final Machine machine, final Deposit deposit,
            final RawMaterial rawMaterial, final Product product) {
        Preconditions.noneNull(quantity, machine);
        setQuantity(quantity);
        this.machine = machine;
        this.deposit = deposit;
        this.rawMaterial = rawMaterial;
        this.product = product;
    }

    public Machine machine() {
        return this.machine;
    }

    public Deposit deposit() {
        return this.deposit;
    }

    public int getQuantaty() {
        return quantity;
    }

    public RawMaterial rawMaterial() {
        return this.rawMaterial;
    }

    public Product product() {
        return this.product;
    }

    private void setQuantity(final int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't have negative values.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "StockIn{" + "quantity=" + quantity + ", machine=" + machine + ", deposit=" + deposit + ", rawMaterial="
                + rawMaterial + ", product=" + product + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.quantity;
        hash = 23 * hash + Objects.hashCode(this.machine);
        hash = 23 * hash + Objects.hashCode(this.deposit);
        hash = 23 * hash + Objects.hashCode(this.rawMaterial);
        hash = 23 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockMovement other = (StockMovement) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.machine, other.machine)) {
            return false;
        }
        if (!Objects.equals(this.deposit, other.deposit)) {
            return false;
        }
        if (!Objects.equals(this.rawMaterial, other.rawMaterial)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    
}
