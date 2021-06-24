/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author bruno
 */
@Embeddable
public class InternalCode implements ValueObject,  Comparable<InternalCode> {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String internalCode;

    protected InternalCode() {
        internalCode=null;
    }

    public InternalCode(final String code) {
        if(!StringPredicates.isNullOrEmpty(code)){
            this.internalCode = code;
        } else {
            throw new IllegalArgumentException("Invalid InternalCode.");
        }
    }   

    @Override
    public int compareTo(InternalCode o) {
        return internalCode.compareTo(o.internalCode);
    }

    @Override
    public String toString() {
        return internalCode;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.internalCode);
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
        final InternalCode other = (InternalCode) obj;
        if (!Objects.equals(this.internalCode, other.internalCode)) {
            return false;
        }
        return true;
    }
    
    
}
