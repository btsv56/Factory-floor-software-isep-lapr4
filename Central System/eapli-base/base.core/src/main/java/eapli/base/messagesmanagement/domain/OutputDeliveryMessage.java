/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author Utilizador
 */
@Entity
public class OutputDeliveryMessage extends Message {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Product delivered
     */
    @ManyToOne
    private Product product;
    /**
     * Quantity of product
     */
    private Integer quantity;
    /**
     * Deposit delivered
     */
    @ManyToOne
    private Deposit deposit;

    protected OutputDeliveryMessage() {
        super();
    }

    /**
     * Constructor for a resumption of Output delivery message
     *
     * @param machine
     * @param dateHour
     * @param type
     * @param product
     * @param deposit
     * @param quantity
     */
    public OutputDeliveryMessage(final Machine machine,final Description type,final LocalDateTime dateHour,final Product product, int quantity,final Deposit deposit) {
        super(machine,type, dateHour);
        Preconditions.noneNull(product, quantity, deposit);
        this.product = product;
        this.deposit = deposit;
        setQuantity(quantity);
    }

    /**
     * sets and validates the quantity
     * @param quantity 
     */
    private void setQuantity(int quantity) {
        if(quantity<=0) {
            throw new IllegalArgumentException("Quantity must be above 0");
        }
        this.quantity=quantity;
    }
    
    public Product product(){
        return product;
    }
    
    public Integer quatity(){
        return quantity;
    }
    
    public Deposit deposit(){
        return deposit;
    }
    
    /**
     * Verifies if two output delivery messages are the same
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final OutputDeliveryMessage outputDeliveryMessage = (OutputDeliveryMessage) other;
        return this.deposit.equals(outputDeliveryMessage.deposit) && this.quantity.equals(outputDeliveryMessage.quantity)
                && this.product.equals(outputDeliveryMessage.product);
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
        return "eapli.base.messagesmanagement.domain.OutputDeliveryMessage[ id=" + super.identity() + " ]";
    }

}
