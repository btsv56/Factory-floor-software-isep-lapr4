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
public class SerialNumber implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String serialNumber;

    protected SerialNumber() {
        this.serialNumber = null;
    }

    public SerialNumber(String rawCode) {
        if (!StringPredicates.isNullOrEmpty(rawCode)) {
            this.serialNumber = rawCode;
        } else {
            throw new IllegalArgumentException("Invalid InternalCode.");
        }
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
        final SerialNumber other = (SerialNumber) obj;
        return Objects.equals(this.serialNumber, other.serialNumber);
    }

    @Override
    public String toString() {
        return serialNumber;

    }
}
