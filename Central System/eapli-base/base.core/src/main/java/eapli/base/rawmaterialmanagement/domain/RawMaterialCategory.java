/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author Bruno Veiga
 */
@Entity
public class RawMaterialCategory implements AggregateRoot<String> {

    private static final long serialVersionUID = 1L;
    //ORM primary key
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Long version;

    //business id
    /**
     * name of raw material category
     */
    @Column(unique = true, nullable = false)
    private String name;
    /**
     * Description of raw material description
     */
    private String description;

    protected RawMaterialCategory() {
        //ORM
    }

    /**
     * Constructor for a raw material category
     *
     * @param name: name of the category
     * @param description: description of the category
     */
    public RawMaterialCategory(final String name, final String description) {
        setname(name);
        setDescription(description);
    }

    /**
     * Sets and validates newname.
     *
     * @param newname
     */
    private void setname(String newname) {
        if (nameMeetsMinimumRequirements(newname)) {
            this.name = newname;
        } else {
            throw new IllegalArgumentException("Invalid name");
        }
    }

    /**
     * Sets and validates newDescription.
     *
     * @param newDescription
     */
    private void setDescription(final String newDescription) {
        if (descriptionMeetsMinimumRequirements(newDescription)) {
            this.description = newDescription;
        } else {
            throw new IllegalArgumentException("Invalid Description");
        }
    }

    /**
     * Ensure name is not null or empty.
     *
     * @param name
     * @return True if name meets minimum requirements. False if name does not
     * meet minimum requirements.
     */
    private static boolean nameMeetsMinimumRequirements(final String name) {
        return (!StringPredicates.isNullOrEmpty(name) && name.length()<=10);
    }

    /**
     * Ensure description is not null or empty.
     *
     * @param description
     * @return True if description meets minimum requirements. False if
     * description does not meet minimum requirements.
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    /**
     * Returns raw material category description
     * @return raw material category description
     */
    public String description() {
        return this.description;
    }

    /**
     * Verifies if two objects are exactly the same
     *
     * @param other: other object
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final RawMaterialCategory rawMaterialCategory = (RawMaterialCategory) other;
        return this.equals(rawMaterialCategory) && this.name.equals(rawMaterialCategory.name) && this.description.equals(rawMaterialCategory.description);
    }

    /**
     * Returns the name of the category
     *
     * @return identity of object
     */
    @Override
    public String identity() {
        return this.name;
    }

    /**
     * Hashcode of an RawMaterialCategory object
     *
     * @return RawMaterialCategory object
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Verifies if two objects have the same id
     *
     * @param o: other object
     * @return
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Return a description of an object RawMaterialCategory
     *
     * @return description of an RawMaterialCategory object
     */
    @Override
    public String toString() {
        return String.format("%s - %s", name,description);
    }

}
