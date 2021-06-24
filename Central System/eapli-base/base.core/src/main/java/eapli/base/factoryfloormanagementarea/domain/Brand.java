/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import java.util.Objects;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;

/**
 *
 * @author bruno
 */
@Embeddable
public class Brand implements ValueObject, Comparable<Brand> {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String brand;

    protected Brand() {
        this.brand = null;
    }

    public Brand(String rawName) {
        this.brand = rawName;
    }

    @Override
    public int compareTo(Brand o) {
        return brand.compareTo(o.brand);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.brand);
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
        final Brand other = (Brand) obj;
        if (!Objects.equals(this.brand, other.brand)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(brand);
        return sb.toString();
    }
    
    
    
    
}
