package eapli.base.productionlinemanagement.domain;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Preconditions;
import eapli.framework.validations.StringPredicates;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class ProductionLine implements AggregateRoot<Designation>{

    private static final long serialVersionUID = 19L;
    @Version
    private Long version;

    //Primary key
    @Id
    private Designation internalCode;

    //Description
    private String desc;

    @OneToMany
    private List<Machine> machines=new LinkedList<>();

    public ProductionLine(final Designation code, final String desc) {
        Preconditions.noneNull(code, desc);
        this.internalCode=code;
        setDesc(desc);
    }

    protected ProductionLine() {

    }

    /**
     * Validates and sets description
     * @param desc
     */
    public void setDesc(final String desc) {
        if (descriptionMeetsMinimumRequirements(desc)) this.desc = desc;
        else throw new IllegalArgumentException("Invalid description");
    }

    public String getDesc() {
        return this.desc;
    }

    public List<Machine> getMachines() {
        return this.machines;
    }

    /**
     * Checks if description is null or empty
     * @param description
     * @return True if it's not null or empty
     */
    private static boolean descriptionMeetsMinimumRequirements(final String description) {
        return !StringPredicates.isNullOrEmpty(description);
    }

    public boolean addMachine(final Machine machine) {
        if (!checkMachines(machine)) return false;
        return machines.add(machine);
    }

    public void addMachine(final Machine machine, int place) {
        if (checkMachines(machine))
        machines.add(place, machine);
    }

    private boolean checkMachines(final Machine machine) {
        for (Machine mach : machines) {
            if (mach.equals(machine)) {
                return false;
            }
        }
        return true;
    }

    public Machine removeMachine(int place) {
        return machines.remove(place);
    }

    public boolean removeMachine(Machine machine) {
        return machines.remove(machine);
    }

    public boolean removeMachine(InternalCode code) {
        for (Machine machine : machines) {
            if (machine.compareTo(code)==0) return machines.remove(machine);
        }
        return false;
    }
    
    public List<Machine> machines(){
        return new LinkedList<>(machines);
    }
    
    /**
     * Checks if two objects are the same
     * @param other: the other object
     * @return True if they're the same object, false otherwise
     */
    @Override
    public boolean sameAs(Object other) {
        final ProductionLine pl=(ProductionLine) other;
        return this.equals(pl) && this.desc.equals(pl.desc);
    }

    /**
     * Returns the identity of this object
     * @return the deposit's code
     */
    @Override
    public Designation identity() {
        return this.internalCode;
    }

    /**
     * HashCode
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    /**
     * Checks if two objects have the same ID
     * @param o: other object
     * @return True if they have the same ID
     */
    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public String toString() {
        StringBuilder out= new StringBuilder("Production Line ID=" + internalCode+ "\n");
        for (Machine mach : machines) {
            out.append("    ").append(mach.toString()).append("\n");
        }
        return out.toString();
    }

    public String toStringSimple(){
        return String.format("Production Line [" + internalCode+ "]");
    }

}
