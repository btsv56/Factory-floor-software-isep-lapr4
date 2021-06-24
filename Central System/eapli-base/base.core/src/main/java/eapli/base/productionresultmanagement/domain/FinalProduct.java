/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.productionresultmanagement.domain;

import eapli.base.productionordermanagement.domain.Lot;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.ValueObject;
import java.util.Objects;
import javax.persistence.OneToOne;

/**
 *
 * @author bruno
 */
@Entity
public class FinalProduct implements ValueObject {
    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue
    private int id;
    
    private final int quantity;
    private final Lot lot;
    @OneToOne
    private final Product product;

    protected FinalProduct() {
        this.quantity = 0;
        this.lot = null;
        this.product = null;
    }

    public FinalProduct(final int quantity, final Lot lot, final Product product) {
        this.quantity = quantity;
        this.lot = lot;
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.quantity;
        hash = 11 * hash + Objects.hashCode(this.lot);
        hash = 11 * hash + Objects.hashCode(this.product);
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
        final FinalProduct other = (FinalProduct) obj;
        if (this.quantity != other.quantity) {
            return false;
        }
        if (!Objects.equals(this.lot, other.lot)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FinalProduct{" + "quantity=" + quantity + ", lot=" + lot + ", product=" + product + '}';
    }
    
    
    
}
