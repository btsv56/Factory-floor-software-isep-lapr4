package eapli.base.productionordermanagement.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Preconditions;
import eapli.framework.validations.StringPredicates;

@Entity
public class ProductionOrder implements AggregateRoot<Designation>{

    private static final long serialVersionUID = 1L;
    @Version
    private long version;

    //Primary key
    @EmbeddedId
    @Column(name="internalCode")
    private Designation internalCode;

    //Description
    @Column(name="description")
    private String desc;

    //Value objects
    @Column(name="state")
    private State state;
    @Column(name="lot")
    private Lot lot;
    @Column(name="request")
    private Request request;
    @Column(name="emissionDate")
    private EmissionDate emissionDate;
    @Column(name="predictedExecDate")
    private PredictedExecutionDate predictedExecutionDate;
    @Column(name="quantity")
    private int quantityToProduce;
    @Enumerated(EnumType.STRING)
    private ProcessingState processingState;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productionLineID")
    private ProductionLine productionLine;

    //Production Sheets
    @ManyToOne
    private ProductionSheet prodSheet;

    public ProductionOrder(final Designation internalCode, final String desc) {
        Preconditions.noneNull(internalCode, desc);
        this.internalCode=internalCode;
        setDesc(desc);
        processingState = ProcessingState.UNPROCESSED;
    }

    protected ProductionOrder() {

    }

    /**
     * Checks if description is null or empty
     * @param description
     * @return True if it's not null or empty
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    /**
     * Sets state (either suspended or pending)
     * @param suspended - boolean
     */
    public void setState(final boolean suspended) {
        this.state = new State(suspended);
    }

    /**
     * Sets state (any state)
     * 
     * @param state - String
     */
    public void setState(final String state) {
        this.state = new State(state);
    }

    /**
     * Sets lot
     * 
     * @param lot - Lot
     */
    public void setLot(final Lot lot) {
        this.lot = lot;
    }

    /**
     * Sets request
     * 
     * @param request - Request
     */
    public void setRequest(final Request request) {
        this.request = request;
    }

    /**
     * Sets emissionDate (current date)
     */
    public void setEmissionDate() {
        this.emissionDate = new EmissionDate();
    }

    /**
     * Sets emissionDate (specified date)
     * 
     * @param date - Calendar
     */
    public void setEmissionDate(final Calendar date) {
        this.emissionDate = new EmissionDate(date);
    }

    /**
     * Sets predictedExecutionDate
     * 
     * @param date - Date
     */
    public void setPredictedExecutionDate(final Calendar date) {
        this.predictedExecutionDate = new PredictedExecutionDate(date);
    }

    /**
     * Sets Production Sheet
     * 
     * @param prodSheet
     */
    public void setProductionSheet(final ProductionSheet prodSheet) {
        this.prodSheet = prodSheet;
    }

    /**
     * Set processingState as PROCESSED
     */
    public void processed() {
        this.processingState = ProcessingState.PROCESSED;
    }

    /**
     * Gets processingState
     * 
     * @return
     */
    public ProcessingState processingState() {
        return processingState;
    }

    /**
     * Validates and sets description
     * 
     * @param desc
     */
    public void setDesc(final String desc) {
        if (descriptionMeetsMinimumRequirements(desc))
            this.desc = desc;
        else
            throw new IllegalArgumentException("Invalid description");
    }

    /**
     * Sets quantityToProduce
     * 
     * @param quantity - int
     */
    public void setQuantityToProduce(final int quantity) {
        this.quantityToProduce = quantity;
    }

    /**
     * Gets internal code
     * 
     * @return Designation internal code
     */
    public Designation getInternalCode() {
        return internalCode;
    }

    /**
     * Gets description
     * 
     * @return String desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Gets state
     * 
     * @return State state
     */
    public State getState() {
        return state;
    }

    /**
     * Gets lot
     * 
     * @return Lot lot
     */
    public Lot getLot() {
        if (lot == null)
            return null;
        return lot;
    }

    /**
     * Gets request
     * 
     * @return Request request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Gets emission date
     * 
     * @return EmissionDate emissionDate
     */
    public EmissionDate getEmissionDate() {
        return emissionDate;
    }

    /**
     * Gets predicted execution date
     * 
     * @return PredictedExecutionDate predictedExecutionDate
     */
    public PredictedExecutionDate getPredictedExecutionDate() {
        return predictedExecutionDate;
    }

    /**
     * Gets production sheet
     * 
     * @return ProduyctionSheet productionSheet
     */
    public ProductionSheet getProductionSheet() {
        return prodSheet;
    }

    /**
     * Gets quantity to produce
     * 
     * @return int quantityToProduce
     */
    public int getQuantityToProduce() {
        return quantityToProduce;
    }

    /**
     * Decrements quantity to produce
     * 
     * @return int quantityToProduce
     */
    public int decrementQuantity() {
        return --quantityToProduce;
    }

    /**
     * Checks if its state is pending or suspended
     * 
     * @return true if either suspended or pending, false otherwise
     */
    public boolean isPendingOrSuspended() {
        return state.isSuspended() || state.isPending();
    }

    /**
     * Checks if it has Lot with lotCode
     * 
     * @param lotCode - Designation
     * @return true if it does, false otherwise
     */
    public boolean hasLot(final Designation lotCode) {
        return this.lot.hasLot(lotCode);
    }

    /**
     * Checks if it has Request with requestCode
     * 
     * @param requestCode - Designation
     * @return true if it does, false otherwise
     */
    public boolean hasRequest(final Designation requestCode) {
        return this.request.hasRequest(requestCode);
    }

    /**
     * Checks if it has State with state
     * 
     * @param state - String
     * @return true if it does, false otherwise
     */
    public boolean hasState(final String state) {
        return this.state.equals(state);
    }

    /**
     * Checks if two objects are the same
     * 
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(final Object other) {
        final ProductionOrder pOrder = (ProductionOrder) other;
        return this.equals(pOrder) && this.desc.equals(pOrder.desc);
    }

    /**
     * Returns the identity of this object
     * 
     * @return the deposit's code
     */
    @Override
    public Designation identity() {
        return this.internalCode;
    }

    /**
     * HashCode
     * 
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if two objects have the same ID
     * 
     * @param o: other object
     * @return True if they have the same ID
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    /**
     * Normal toString
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Production Order " + internalCode.toString() + "\n   - Description: " + desc + "\n   - State: "
                + state.toString();
    }

    public String toStringSimple() {
        return "Production Order " + "[" + internalCode + "]";
    }

    /**
     * Complete toString for consult
     * 
     * @return String
     */
    public String toStringComplete() {
        return "ID: " + internalCode.toString() + "\n   - Lot: " + lot.toString() + "\n   - Request: "
                + request.toString() + "\n   - Emission date: " + emissionDate.toString()
                + "\n   - Predicted execution date: " + predictedExecutionDate.toString()
                + "\n   - Production Sheet ID: " + prodSheet.identity().toString()
                + "\n     - Quantity to be produced: " + quantityToProduce;
    }

    public ProductionLine productionLine() {
        return productionLine;
    }

    public void assign(final ProductionLine toProductionLine) {
        this.productionLine = toProductionLine;
	}

}
