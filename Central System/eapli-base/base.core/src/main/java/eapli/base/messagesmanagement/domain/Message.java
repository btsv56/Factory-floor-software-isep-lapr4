/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionordermanagement.domain.ProcessingState;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author Utilizador
 */
@Table(
    uniqueConstraints=
        @UniqueConstraint(columnNames={"machine", "dateHour","TheDescription"})
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Machine message refers to
     */
    @ManyToOne
    @JoinColumn(name = "machine")
    private Machine machine;
    /**
     * Date and hour sent
     */
    @Column(name = "dateHour")
    private LocalDateTime dateHour;
    /**
     * type of message
     */
    private Description type;
    @Enumerated(EnumType.STRING)
    private ProcessingState state;

    protected Message() {
        //ORM
    }

    /**
     * Constructor for a message
     *
     * @param machine
     * @param dateHour
     * @param type
     */
    public Message(final Machine machine,final Description type,final LocalDateTime dateHour) {
        Preconditions.noneNull(machine, dateHour, type);
        this.machine = machine;
        setDateHour(dateHour);
        this.type = type;
        this.state = ProcessingState.UNPROCESSED;
    }
    
    private void setDateHour(LocalDateTime dateHour) {
        if(!dateHour.isAfter(LocalDateTime.now().plusDays(1))) {
            this.dateHour=dateHour;
        }else{
            throw new IllegalArgumentException("Future date for message");
        }
    }
    
    public void processed(){
        this.state = ProcessingState.PROCESSED;
    }
    
    public ProcessingState state(){
        return state;
    }
    
    public Machine machine(){
        return machine;
    }
    
    public Description type(){
        return type;
    }
    
    public LocalDateTime dateHour(){
        return dateHour;
    }
    
    public Date date(){
        return Timestamp.valueOf(dateHour);
    }
    
    /**
     * Verifies if two start of activity messages are the same
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final Message startOfActivityMessage = (Message) other;
        return this.machine.equals(startOfActivityMessage.machine) && this.dateHour.equals(startOfActivityMessage.dateHour)
                && this.type.equals(startOfActivityMessage.type);
    }

    /**
     * Returns the identity of the message
     *
     * @return
     */
    @Override
    public Long identity() {
        return this.id;
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
        return "eapli.base.messagesmanagement.domain.Message[ id=" + id + " ]";
    }
    
    
}
