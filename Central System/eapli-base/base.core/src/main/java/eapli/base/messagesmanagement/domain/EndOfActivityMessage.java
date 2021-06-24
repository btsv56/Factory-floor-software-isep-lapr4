/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;

/**
 *
 * @author Utilizador
 */
@Entity
public class EndOfActivityMessage extends Message {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Production order that ended production
     */
    @ManyToOne
    @JoinColumn(nullable = true)
    private ProductionOrder productionOrder;

    protected EndOfActivityMessage() {
        super();
    }

    /**
     * Constructor for a end of activity message
     *
     * @param machine
     * @param dateHour
     * @param type
     * @param productionOrder
     */
    public EndOfActivityMessage(final Machine machine, final Description type, final LocalDateTime dateHour, final ProductionOrder productionOrder) {
        super(machine, type, dateHour);
        this.productionOrder = productionOrder;
    }
    
    public ProductionOrder order(){
        return productionOrder;
    }

    /**
     * Verifies if two start of activity messages are the same
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final EndOfActivityMessage endOfActivityMessage = (EndOfActivityMessage) other;
        return super.sameAs(other) && this.productionOrder.equals(endOfActivityMessage.productionOrder);
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
        return "eapli.base.messagesmanagement.domain.EndOfActivityMessage[ id=" + super.identity() + " ]";
    }

}
