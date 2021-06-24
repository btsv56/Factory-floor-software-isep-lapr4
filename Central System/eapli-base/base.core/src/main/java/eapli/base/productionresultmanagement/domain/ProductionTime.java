package eapli.base.productionresultmanagement.domain;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.domain.model.ValueObject;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class ProductionTime implements ValueObject {

    private static final long serialVersionUID = 1L;
    @Version
    private Long version;

    //Primary key
    @Id
    @GeneratedValue
    private int id;
    
    @OneToOne(fetch=FetchType.LAZY)
    private final Machine machine;
    @Temporal(TemporalType.DATE)
    private final Date startTime;
    @Temporal(TemporalType.DATE)
    private final Date endTime;

    protected ProductionTime() {
        this.machine = null;
        this.startTime = null;
        this.endTime = null;
    }

    public ProductionTime(Machine machine, Date startTime, Date endTime) {
        this.machine = machine;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return String.valueOf(duration());
    }
    
    
    public long duration(){
        return endTime.getTime() - startTime.getTime();
    }
    
    public long minutes(){
        return TimeUnit.MINUTES.convert(duration(), TimeUnit.MILLISECONDS);
    }
    
    public long seconds(){
        return TimeUnit.SECONDS.convert(duration() - TimeUnit.MINUTES.convert(minutes(), TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
    }
    
    public Date startTime(){
        return startTime;
    }
    
    public Date endTime(){
        return endTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.machine);
        hash = 11 * hash + Objects.hashCode(this.startTime);
        hash = 11 * hash + Objects.hashCode(this.endTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductionTime other = (ProductionTime) obj;
        if (!Objects.equals(this.machine, other.machine)) {
            return false;
        }
        if (!Objects.equals(this.startTime, other.startTime)) {
            return false;
        }
        if (!Objects.equals(this.endTime, other.endTime)) {
            return false;
        }
        return true;
    }

    public Machine machine() {
        return machine;
    }
    
}
