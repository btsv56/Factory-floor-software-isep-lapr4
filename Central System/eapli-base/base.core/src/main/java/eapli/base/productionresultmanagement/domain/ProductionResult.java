package eapli.base.productionresultmanagement.domain;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.utils.Converter;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class ProductionResult implements AggregateRoot<Long>{

    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue
    private Long internalCode;
    @OneToOne
    private ProductionOrder productionOrder;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTION_RESULT_STOCK_IN")
    private final Set<StockMovement> stockIn;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTION_RESULT_STOCK_OUT")
    private final Set<StockMovement> stockOut;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTION_RESULT_EFFECTIVE_TIMES")
    private final Set<ProductionTime> effectiveTimes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PRODUCTION_RESULT_DOWN_TIMES")
    private final Set<ProductionTime> downTimes;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final Set<FinalProduct> products;

    public ProductionResult() {
        productionOrder = null;
        this.stockIn = new HashSet<>();
        this.stockOut = new HashSet<>();
        this.effectiveTimes = new HashSet<>();
        this.downTimes = new HashSet<>();
        this.products = new HashSet<>();
    }

    void setProductionOrder(final ProductionOrder order) {
        this.productionOrder = order;
    }

    void addStockIn(final Iterable<StockMovement> stock) {
        Preconditions.nonNull(stock);
        stockIn.addAll(Converter.iterableToSet(stock));
    }

    void addStockOut(final Iterable<StockMovement> stock) {
        Preconditions.nonNull(stock);
        stockOut.addAll(Converter.iterableToSet(stock));
    }

    void addEffectiveTime(final Iterable<ProductionTime> time) {
        Preconditions.nonNull(time);
        effectiveTimes.addAll(Converter.iterableToSet(time));
    }

    void addDownTime(final Iterable<ProductionTime> time) {
        Preconditions.nonNull(time);
        downTimes.addAll(Converter.iterableToSet(time));
    }

    void addFinalProduct(final Iterable<FinalProduct> product) {
        Preconditions.nonNull(product);
        products.addAll(Converter.iterableToSet(product));
    }

    public Iterable<StockMovement> consumptions() {
        return this.stockOut;
    }

    public ProductionOrder order() {
        return productionOrder;
    }

    /**
     * Checks if two objects are the same
     *
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    /**
     * Returns the identity of this object
     *
     * @return the deposit's code
     */
    @Override
    public Long identity() {
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

    @Override
    public String toString() {
        return "ProductionResult{" + "deviation=" + '}';
    }

    boolean isValid() {
        return true;
        // !stockOut.isEmpty() &&
        // !effectiveTimes.isEmpty() &&
        // productionOrder != null;
    }

    public Iterable<StockMovement> getWastes() {
        return stockIn;
    }

    public Iterable<ProductionTime> getEffectiveTimes() {
        return effectiveTimes;
    }

    public Iterable<ProductionTime> getBruteTimes() {
        final List<ProductionTime> list = new LinkedList<>();
        Date earlier = null, later = null;
        for (final ProductionTime et : effectiveTimes) {
            if (earlier == null || earlier.after(et.startTime())) {
                earlier = et.startTime();
            }
            if (later == null || later.before(et.endTime())) {
                later = et.endTime();
            }
            for (final ProductionTime dt : downTimes) {
                if (et.machine().equals(dt.machine())) {
                    if (earlier.after(dt.startTime())) {
                        earlier = dt.startTime();
                    }
                    if (later.before(dt.endTime())) {
                        later = dt.endTime();
                    }
                }
            }
            list.add(new ProductionTime(et.machine(), earlier, later));
        }
        return list;
    }

}
