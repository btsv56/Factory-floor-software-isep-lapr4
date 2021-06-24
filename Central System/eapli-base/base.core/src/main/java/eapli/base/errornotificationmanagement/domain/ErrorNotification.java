/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.domain;

import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;

/**
 *
 * @author Utilizador
 */
@Entity
public class ErrorNotification implements AggregateRoot<Long> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = true)
    private Machine machine;
    /**
     * State of error notification
     */
    private ErrorNotificationState errorNotificationState;
    /**
     * Description of error
     */
    @AttributeOverrides({ @AttributeOverride(name = "theDescription", column = @Column(name = "description")) })
    private Description description;
    /**
     * Type of error
     */
    @AttributeOverrides({
            @AttributeOverride(name = "theDescription", column = @Column(name = "errorNotificationType")) })
    private ErrorNotificationType errorNotificationType;
    /**
     * Date time of the error
     */
    private LocalDateTime dateTime;
    /**
     * Message type
     */
    private Description messageType;

    protected ErrorNotification() {
        // ORM
    }

    /**
     * Constructor for an error notification
     *
     * @param description
     * @param type
     * @param messageType
     */
    public ErrorNotification(final Machine machine, final Description description, final ErrorNotificationType type,
            final Description messageType, final LocalDateTime dateTime) {
        Preconditions.noneNull(description, type, messageType);
        this.machine = machine;
        this.description = description;
        this.errorNotificationType = type;
        this.messageType = messageType;
        this.dateTime = dateTime;
        errorNotificationState = new ErrorNotificationState(StateEnum.UNTREATED);
    }

    /**
     * This method verifies if this instance has the received instance as a
     * parameter
     *
     * @param type
     * @return
     */
    public boolean hasNotificationType(ErrorNotificationType type) {
        if (this.errorNotificationType.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method verifies if this instance has the received instance as a
     * parameter
     *
     * @param machine
     * @return
     */
    public boolean hasMachine(Machine machine) {
        if (this.machine.sameAs(machine)) {
            return true;
        } else if (this.machine.sameAs(null)) {
            return false;
        } else {
            return false;
        }
    }

    public boolean hasState(StateEnum stateEnum) {
        return this.errorNotificationState.hasState(stateEnum);
    }

    public void archive() {
        this.errorNotificationState = new ErrorNotificationState(StateEnum.ARCHIVED);
    }

    /**
     * Verifies if two objects are equal
     *
     * @param other
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final ErrorNotification errorNotification = (ErrorNotification) other;
        return this.description.equals(errorNotification.description)
                && this.errorNotificationState.equals(errorNotification.errorNotificationState)
                && this.messageType.equals(errorNotification.messageType)
                && this.errorNotificationType.equals(errorNotification.errorNotificationType);
    }

    /**
     * Identity of error notification
     *
     * @return
     */
    @Override
    public Long identity() {
        return this.id;
    }

    /**
     * Hashcode of an Error notification object
     *
     * @return hashcode of a RawMaterial object
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Verifies if two objects have the same id
     *
     * @param o: other object
     * @return true if two objects have the same id
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * To String of error notification
     *
     * @return error notification
     */
    @Override
    public String toString() {
        return "id= " + id + "\nRrror notification state= " + errorNotificationState + "\nDescription= " + description
                + "\neError Notification Type= " + errorNotificationType + "\nMessage Type= " + messageType;
    }

    public String toStringHorizontal() {
        return "Notification[" + id + "] : State = \'" + errorNotificationState + "\'; Description = \'" + description
                + "\'; Type = \'" + errorNotificationType + "\'; Message = \'" + messageType + "\'";
    }

    public ErrorNotificationType type() {
        return errorNotificationType;
    }

    public boolean hasAnyNotificationType(Iterable<ErrorNotificationType> types) {
        for (ErrorNotificationType type : types) {
            if (this.errorNotificationType.equals(type))
                return true;
        }
        return false;
    }

    public boolean hasAnyProductionLine(Iterable<ProductionLine> lines) {
        for (ProductionLine line : lines) {
            for (Machine m : line.getMachines())
                if (this.machine.sameAs(m))
                    return true;
        }
        return false;
    }
}
