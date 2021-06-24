/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;

/**
 *
 * @author Utilizador
 */
@Entity
public class ForcedStopMessage extends Message{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected ForcedStopMessage() {
        super();
    }

    /**
     * Constructor for a resumption of activity message
     *
     * @param machine
     * @param dateHour
     * @param type
     */
    public ForcedStopMessage(final Machine machine,final Description type,final LocalDateTime dateHour) {
        super(machine,type, dateHour);
    }

    /**
     * Returns the identity of the message
     *
     * @return
     */
    @Override
    public Long identity() {
        return super.identity();
    }

    /**
     * Hash code
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Equals method
     *
     * @param o
     * @return if two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * To string
     *
     * @return to string
     */
    @Override
    public String toString() {
        return "eapli.base.messagesmanagement.domain.ForcedStopMessage[ id=" + super.identity() + " ]";
    }

}
