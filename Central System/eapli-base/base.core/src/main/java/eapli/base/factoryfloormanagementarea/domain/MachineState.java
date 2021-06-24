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
public class MachineState implements ValueObject {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String machineState;

    protected MachineState() {
        this.machineState = null;
    }
    
    public MachineState(String rawState){
        if (!StringPredicates.isNullOrEmpty(rawState)) {
            this.machineState = rawState;
        } else {
            throw new IllegalArgumentException("Invalid InternalCode.");
        }
    }

    public String machineState(){
        return this.machineState;
    }
    
    
}
