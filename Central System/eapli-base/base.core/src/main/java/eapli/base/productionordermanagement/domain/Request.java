package eapli.base.productionordermanagement.domain;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.model.general.Designation;

@Embeddable
public class Request implements ValueObject, Comparable<Request> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @AttributeOverrides({
            @AttributeOverride(name="name", column=@Column(name="requestCode")) })
    private Designation requestCode;

    public Request(final Designation requestCode) {
        this.requestCode=requestCode;
    }

    protected Request() {

    }

    public boolean hasRequest(Designation requestCode) {
        return this.requestCode.equals(requestCode);
    }

    @Override
    public int compareTo(Request o) {
        return requestCode.compareTo(o.requestCode);
    }

    @Override
    public String toString() {
        return requestCode.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.requestCode);
        return hash;
    }
}
