/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author Utilizador
 */
@Entity
public class ResumptionOfActivityMessage extends Message {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Error that was generated
     */
    @AttributeOverrides({@AttributeOverride(name="theDescription", column=@Column(name="error")) })
    private Description error;
    
    protected ResumptionOfActivityMessage() {
        super();
    }

   /**
     * Constructor for a resumption of activity message
     *
     * @param machine
     * @param dateHour
     * @param type
     * @param error
     */
    public ResumptionOfActivityMessage(final Machine machine,final Description type,final LocalDateTime dateHour,final  Description error) {
        super(machine,type, dateHour);
        Preconditions.nonNull(error);
        this.error = error;
    }

    /**
     * Verifies if two resumption of activity messages are the same
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final ResumptionOfActivityMessage resumptionOfActivityMessage = (ResumptionOfActivityMessage) other;
        return super.sameAs(other) && this.error.equals(resumptionOfActivityMessage.error);
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
        return "eapli.base.messagesmanagement.domain.ResumptionOfActivityMessage[ id=" + super.identity() + " ]";
    }

}
