package eapli.base.productmanagement.domain;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Preconditions;
import eapli.framework.validations.StringPredicates;

import javax.persistence.*;

@Entity
public class Product implements AggregateRoot<Designation> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Version
    private Long version;
    /**
     * Fabrication fabricationCode of the Product
     */
    @EmbeddedId
    private Designation fabricationCode;
    /**
     * Comercial fabricationCode of the Product
     */
    private String comercialCode;
    /**
     * Brief description of the Product
     */
    private String briefDescription;
    /**
     * Complete description of the Product
     */
    private String completeDescription;
    /**
     * productCategory Of the Product
     */
    private String productCategory;
    /**
     * Unity of the Product
     */
    private String unity;

    @OneToOne
    private ProductionSheet productionSheet;


    /**
     * Constructs a product
     * @param fabricationCode
     * @param comercialCode
     * @param briefDescription
     * @param completeDescription
     * @param unity
     * @param productCategory
     */
    public Product(final Designation fabricationCode, final String comercialCode, final String briefDescription, final String completeDescription, final String unity, final String productCategory){
        addfabricationCode(fabricationCode);
        addcomercialCode(comercialCode);
        addBriefDescription(briefDescription);
        addCompleteDescription(completeDescription);
        addproductCategory(productCategory);
        addUnity(unity);
        this.productionSheet = null;
    }

    protected Product() {
        // ORM
    }

    public void addProductionSheet(ProductionSheet productionSheet){
        this.productionSheet = productionSheet;
    }

    public boolean hasProductionSheet(){
        if(productionSheet == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifies and if the fabrication code is within the requirements adds it
     * @param fabricationCode
     */
    private void addfabricationCode(Designation fabricationCode){
        if(fabricationCodeMeetsBusinessRequirements(fabricationCode)){
            this.fabricationCode = fabricationCode;
        } else {
            throw new IllegalArgumentException("Invalid Fabrication fabricationCode");
        }
    }

    /**
     * Verifies and if the comercial code is within the requirements adds it
     * @param comercialCode
     */
    private void addcomercialCode(String comercialCode){
        if(comercialCodeMeetsBusinessRequirements(comercialCode)){
            this.comercialCode = comercialCode;
        } else {
            throw new IllegalArgumentException("Invalid Comercial fabricationCode");
        }
    }

    /**
     * Verifies and if the brief description is within the requirements adds it
     * @param briefDescription
     */
    private void addBriefDescription(String briefDescription){
        if(briefDescriptionMeetsBusinessRequirements(briefDescription)){
            this.briefDescription = briefDescription;
        } else {
            throw new IllegalArgumentException("Invalid Brief Description");
        }
    }

    /**
     * Verifies and if the complete description is within the requirements adds it
     * @param completeDescription
     */
    private void addCompleteDescription(String completeDescription){
        if(completeDescriptionMeetsBusinessRequirements(completeDescription)){
            this.completeDescription = completeDescription;
        } else {
            throw new IllegalArgumentException("Invalid Complete Description");
        }
    }

    /**
     * Verifies and if the category is within the requirements adds it
     * @param productCategory
     */
    private void addproductCategory(String productCategory){
        if(productCategoryMeetsBusinessRequirements(productCategory)){
            this.productCategory = productCategory;
        } else {
            throw new IllegalArgumentException("Invalid productCategory");
        }
    }

    /**
     * Verifies and if the unity is within the requirements adds it
     * @param unity
     */
    private void addUnity(String unity){
        if(unityMeetsBusinessRequirements(unity)){
            this.unity = unity;
        } else {
            throw new IllegalArgumentException("Invalid unity");
        }
    }

    /**
     * Verifies if the product category meets the business requirements
     * @param productCategory
     * @return
     */
    private static boolean productCategoryMeetsBusinessRequirements(final String productCategory){
        return !StringPredicates.isNullOrEmpty(productCategory);
    }

    /**
     * Verifies if the unity meets the business requirements
     * @param unity
     * @return
     */
    private static boolean unityMeetsBusinessRequirements(final String unity){
        return !StringPredicates.isNullOrEmpty(unity);
    }

    /**
     * Verifies if the fabrication code meets the business requirements
     * @param fabricationCode
     * @return
     */
    private static boolean fabricationCodeMeetsBusinessRequirements(final Designation fabricationCode){
        Preconditions.nonNull(fabricationCode);
        return fabricationCode.toString().length() <= 15;
    }

    /**
     * Verifies if the comercial code meets the business requirements
     * @param comercialCode
     * @return
     */
    private static boolean comercialCodeMeetsBusinessRequirements(final String comercialCode){
        return comercialCode.length() <= 15 && !StringPredicates.isNullOrEmpty(comercialCode);
    }

    /**
     * verifies if the complete description meets the bussiness requirements
     * @param completeDescription
     * @return
     */
    private static boolean completeDescriptionMeetsBusinessRequirements(final String completeDescription){
        return !StringPredicates.isNullOrEmpty(completeDescription);
    }

    /**
     * Verifies if the brief description meets the business requirements
     * @param briefDescription
     * @return
     */
    private static boolean briefDescriptionMeetsBusinessRequirements(final String briefDescription){
        return !StringPredicates.isNullOrEmpty(briefDescription);
    }

    /**
     * Verifies if two instances of an object
     * is exactly the same
     *
     * @param other object to be compared
     * @return true if everything is the same
     */
    @Override
    public boolean sameAs(Object other) {
        final Product product = (Product) other;
        return this.equals(product)
                && this.fabricationCode.equals(product.fabricationCode)
                && this.comercialCode.equals(product.comercialCode)
                && this.briefDescription.equals(product.briefDescription)
                && this.completeDescription.equals(product.completeDescription);
    }

    /**
     * Returns the identity of a Product
     *
     * @return identity of the object
     */
    @Override
    public Designation identity() {
        return this.fabricationCode;
    }

    /**
     * Returns the brief description of the Product
     * @return
     */
    public String description() {
        return this.briefDescription;
    }

    public String completeDescription() {
        return this.completeDescription;
    }

    public String comercialCode() {
        return this.comercialCode;
    }

    public String productCategory() {
        return this.productCategory;
    }

    public String unity() {
        return this.unity;
    }

    public String productionSheet() {
        if (this.productionSheet == null) {
            return "";
        } else
            return this.productionSheet.identity().toString();
    }



    /**
     * Verifies if two objects have the same ID
     *
     * @param o Object to be compared
     * @return true if the objects have the same ID
     */
    @Override
    public boolean equals(Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Hashes the Product object
     *
     * @return hash of this object
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Description of a Product
     *
     * @return Product Description
     */
    @Override
    public String toString() {
        return "eapli.base.product.domain.ProductionSheet[ id=" + fabricationCode + " ]";
    }
}
