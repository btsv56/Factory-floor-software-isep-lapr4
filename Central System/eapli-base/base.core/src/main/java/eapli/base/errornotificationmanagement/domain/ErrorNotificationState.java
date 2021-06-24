/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.util.HashCoder;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Utilizador
 */
@Embeddable
public class ErrorNotificationState implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * State of error notification
     */
    @Enumerated(EnumType.STRING)
    private StateEnum stateError;

    
    protected ErrorNotificationState() {
        //ORM
    }

    /**
     * Constructor
     * 
     * @param state 
     */
    public ErrorNotificationState(StateEnum state) {
        this.stateError = state;
    }

    /**
     * Verifies if two objects are the same
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorNotificationState)) {
            return false;
        }

        final ErrorNotificationState that = (ErrorNotificationState) o;
        return this.stateError.equals(that.stateError);
    }

    /**
     * HashCode´
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        return new HashCoder().with(stateError).code();
    }

    /**
     * To String
     * @return 
     */
    @Override
    public String toString() {
        return this.stateError.toString();
    }

    public boolean hasState(StateEnum stateEnum) {
        return this.stateError.equals(stateEnum);
    }
}
