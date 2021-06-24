/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Preconditions;
import eapli.framework.validations.StringPredicates;

/**
 *
 * @author Bruno Veiga
 */
@Entity
public class RawMaterial implements AggregateRoot<Designation> {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;
    
    //internal code
    @EmbeddedId
    private Designation internalCode;
    /**
     * Desription of the raw material
     */
    private String description;
    /**
     * Category of the raw material
     */
    @ManyToOne
    private RawMaterialCategory rawMaterialCategory;
    /**
     * Technical sheet of raw material
     */
    private TechnicalSheet technicalSheet;

    protected RawMaterial() {
        //ORM
    }

    /**
     * Raw Material Constructor
     * @param internalCode: internal code of raw material
     * @param description: description of raw material
     * @param rawMaterialCategory: category of raw material
     * @param technicalSheet : technical sheet of raw material
     */
    public RawMaterial(final Designation internalCode, final String description, final RawMaterialCategory rawMaterialCategory, final TechnicalSheet technicalSheet) {
        Preconditions.noneNull(rawMaterialCategory, technicalSheet);
        this.rawMaterialCategory=rawMaterialCategory;
        this.technicalSheet=technicalSheet;
        setInternalCode(internalCode);
        setDescription(description);
    }

    /**
     * Sets and validates internal code
     * @param internalCode 
     */
    private void setInternalCode(Designation internalCode) {
        if(internalCodeMeetsMinimumRequirements(internalCode)) {
            this.internalCode=internalCode;
        }else{
            throw new IllegalArgumentException("Invalid internal code");
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
     * Ensure description is not null or empty.
     *
     * @param description
     * @return True if description meets minimum requirements. False if description
     *         does not meet minimum requirements.
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }
    
    /**
     * ´Ensures that the internal code has 15 or less characters
     * @param internalCode
     * @return 
     */
    private static boolean internalCodeMeetsMinimumRequirements(final Designation internalCode) {
        Preconditions.nonNull(internalCode);
        return internalCode.toString().length()<=15;
    }
    
    public Designation internalCode() {
        return this.internalCode;
    }
    
    public String category() {
        return this.rawMaterialCategory.identity();
    }
    
    public String technicalSheet() {
        return this.technicalSheet.toString();
    }

    /**
     * Verifies if two objects are exactly the same
     *
     * @param other: other object
     * @return
     */
    @Override
    public boolean sameAs(Object other) {
        final RawMaterial rawMaterial = (RawMaterial) other;
        return this.equals(rawMaterial) && this.description.equals(rawMaterial.description) && this.rawMaterialCategory.equals(rawMaterial.rawMaterialCategory)
                && this.technicalSheet.equals(rawMaterial.technicalSheet);
    }

    /**
     * Returns the description of the raw material
     *
     * @return identity of object
     */
    @Override
    public Designation identity() {
        return this.internalCode;
    }

    public String description() {
        return description;
    }

    /**
     * Hashcode of an RawMaterial object
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
     * Description of an object raw material
     *
     * @return raw material description
     */
    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s", this.internalCode,this.description,this.rawMaterialCategory.description(),this.technicalSheet);
    }

}
