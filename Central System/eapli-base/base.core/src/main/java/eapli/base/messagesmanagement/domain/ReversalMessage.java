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

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author Utilizador
 */
@Entity
public class ReversalMessage extends Message {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Product delivered
     */
    @ManyToOne
    @JoinColumn(nullable = true)
    private Product product;
    /**
     * Raw Material delivered
     */
    @ManyToOne
    @JoinColumn(nullable = true)
    private RawMaterial rawMaterial;
    /**
     * Quantity of product
     */
    private Integer quantity;
    /**
     * Deposit delivered
     */
    @ManyToOne
    private Deposit deposit;

    protected ReversalMessage() {
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
    public ReversalMessage(final Machine machine, final Description type, final LocalDateTime dateHour, final Product product, int quantity, final Deposit deposit) {
        super(machine, type, dateHour);
        Preconditions.noneNull(product, quantity, deposit);
        this.product = product;
        this.deposit = deposit;
        setQuantity(quantity);
    }

    /**
     * Constructor for a resumption of Output delivery message
     *
     * @param machine
     * @param dateHour
     * @param type
     * @param rawMaterial
     * @param deposit
     * @param quantity
     */
    public ReversalMessage(final Machine machine, final Description type, final LocalDateTime dateHour, final RawMaterial rawMaterial, int quantity, final Deposit deposit) {
        super(machine, type, dateHour);
        Preconditions.noneNull(rawMaterial, quantity, deposit);
        this.rawMaterial = rawMaterial;
        this.deposit = deposit;
        setQuantity(quantity);
    }

    /**
     * sets and validates the quantity
     *
     * @param quantity
     */
    private void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be above 0");
        }
        this.quantity = quantity;
    }
    
    public Product product(){
        return product;
    }
    
    public RawMaterial rawMaterial(){
        return rawMaterial;
    }
    
    public Integer quantity(){
        return quantity;
    }
    
    public Deposit deposit(){
        return deposit;
    }
    
    /**
     * Verifies if two reversal messages are the same
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final ReversalMessage reversalMessage = (ReversalMessage) other;
        return this.deposit.equals(reversalMessage.deposit) && this.quantity.equals(reversalMessage.quantity)
                && this.product.equals(reversalMessage.product);
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
        return "eapli.base.messagesmanagement.domain.ReversalMessage[ id=" + super.identity() + " ]";
    }

}
