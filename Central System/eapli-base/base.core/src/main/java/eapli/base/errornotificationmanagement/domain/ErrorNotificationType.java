/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.domain.model.general.Description;
import eapli.framework.util.HashCoder;
import eapli.framework.validations.Preconditions;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Utilizador
 */
@Embeddable
public class ErrorNotificationType implements ValueObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Description of error notificatio type
     */
     @AttributeOverrides({
        @AttributeOverride(name = "theDescription", column = @Column(name = "typeDescription"))})
    private Description typeDescription;

    protected ErrorNotificationType() {
        //ORM
    }

    /**
     * Constructor for an error notification type
     * 
     * @param description 
     */
    public ErrorNotificationType(Description description) {
        Preconditions.noneNull(description);
        this.typeDescription = description;
    }

    /**
     * Verifies if two objects are equal
     * 
     * @param o
     * @return boolean that indicates if two objects are equal
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorNotificationType)) {
            return false;
        }

        final ErrorNotificationType that = (ErrorNotificationType) o;
        return this.typeDescription.equals(that.typeDescription);
    }

    /**
     * HashCode method
     *
     * @return hashcode method
     */
    @Override
    public int hashCode() {
        return new HashCoder().with(typeDescription).code();
    }

    /**
     * To string method
     *
     * @return to string
     */
    @Override
    public String toString() {
        return this.typeDescription.toString();
    }

}
