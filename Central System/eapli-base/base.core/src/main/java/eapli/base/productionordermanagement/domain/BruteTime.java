package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.model.general.Designation;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class BruteTime implements ValueObject, Comparable<BruteTime> {

    private static final long serialVersionUID = 1L;
    
    private Designation internalCode;

    public BruteTime(final Designation internalCode) {
        this.internalCode=internalCode;
    }

    protected BruteTime() {

    }

    @Override
    public int compareTo(BruteTime o) {
        return internalCode.compareTo(o.internalCode);
    }

    @Override
    public String toString() {
        return internalCode.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.internalCode);
        return hash;
    }
}
