package eapli.base.productionordermanagement.domain;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.model.general.Designation;

@Embeddable
public class Lot implements ValueObject,  Comparable<Lot> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @AttributeOverrides({
            @AttributeOverride(name="name", column=@Column(name="lotCode")) })
    private Designation lotCode;

    public Lot(final Designation lotCode) {
        this.lotCode=lotCode;
    }

    protected Lot() {

    }
    
    public boolean hasLot(Designation lotCode) {
        return this.lotCode.equals(lotCode);
    }

    @Override
    public int compareTo(Lot o) {
        return lotCode.compareTo(o.lotCode);
    }

    @Override
    public String toString() {
        return lotCode.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.lotCode);
        return hash;
    }
}
