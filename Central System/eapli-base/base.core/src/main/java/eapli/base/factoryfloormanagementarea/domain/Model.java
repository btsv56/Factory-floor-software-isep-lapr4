/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import javax.persistence.Embeddable;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author bruno
 */
@Embeddable
public class Model implements ValueObject {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String model;
    
    protected Model(){
        model = null;
    }

    public Model(String rawName) {
        if (!StringPredicates.isNullOrEmpty(rawName)) {
            this.model = rawName;
        } else {
            throw new IllegalArgumentException("Invalid InternalCode.");
        }
    }

    public String model(){
        return this.model;
    }
}
