/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.NumberPredicates;

/**
 *
 * @author bruno
 */
@Embeddable
public class Protocol implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Integer MAX_ID = 65535;

    @AttributeOverrides({
            @AttributeOverride(name="ID", column=@Column(name="protocol")) })
    private Integer protocol;

    protected Protocol() {

    }

    public Protocol(Integer protocol) {
        if ( protocol != null && NumberPredicates.isPositive(protocol) && protocol.compareTo(MAX_ID) < 0) {
            this.protocol = protocol;
        } else {
            throw new IllegalArgumentException("Protocol ID must be between 1 and " + MAX_ID);
        }
    }
    
    public boolean hasId(int protocol) {
        return this.protocol==protocol;
    }

    public int getInt() {
        return this.protocol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.protocol);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Protocol other = (Protocol) obj;
        return Objects.equals(this.protocol, other.protocol);
    }

}
