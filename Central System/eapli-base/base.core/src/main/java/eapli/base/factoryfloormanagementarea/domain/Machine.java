/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.general.Description;
import eapli.framework.validations.Preconditions;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author bruno
 */
@Entity
public class Machine implements AggregateRoot<InternalCode> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    
    @EmbeddedId
    private final InternalCode internalCode;
    @Column(unique=true)
    private final SerialNumber serialNumber;
    private Description description;
    @Temporal(javax.persistence.TemporalType.DATE)
    private InstallationDate dateOfInstallation;
    private Brand brand;
    private Model model;
    @Column(unique=true, name="protocol")
    @GeneratedValue
    private Protocol protocol;
    @JoinColumn(name="followedByMachine", referencedColumnName="internalCode",nullable=true)
    @OneToOne(fetch=FetchType.LAZY)
    private Machine followedByMachine;
    private MachineState machineState;
    @OneToMany(cascade=CascadeType.ALL)    
    private final Set<ConfigurationFile> configList;

    protected Machine() {
        this.configList = new HashSet<>();
        internalCode = null;
        serialNumber = null;
    }
    
    public Machine(InternalCode internalCode, SerialNumber serialNumber, Description description, InstallationDate dateOfInstallation, Brand brand, Model model, MachineState state, Protocol protocol) {
        this.configList = new HashSet<>();
        Preconditions.noneNull(internalCode, serialNumber, description, dateOfInstallation, brand, model, state);
        this.internalCode = internalCode;
        this.serialNumber = serialNumber;
        this.description = description;
        this.dateOfInstallation = dateOfInstallation;
        this.brand = brand;
        this.model = model;
        this.protocol=protocol;
        this.followedByMachine = null;
        this.machineState = state;
    }

    public InternalCode internalCode(){
        return internalCode;
    }
    
    public SerialNumber serialNumber(){
        return serialNumber;
    }
    
    public Description description(){
        return description;
    }
    
    public InstallationDate installationDate(){
        return dateOfInstallation;
    }
    
    public Brand brand(){
        return brand;
    }
    
    public Model model(){
        return model;
    }
    
    public Protocol protocol(){
        return protocol;
    }
    
    public Machine followedByMachine(){
        return followedByMachine;
    }
    
    public MachineState state(){
        return machineState;
    }
    
    public Set<ConfigurationFile> configList(){
        return configList;
    }
    
    public void attach(final ConfigurationFile configurationFile){
        Preconditions.nonNull(configurationFile);
        this.configList.add(configurationFile);
    }
    
    public boolean hasId(int id) {
        return this.protocol.hasId(id);
    }

    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean sameAs(Object other) {
        Machine m = (Machine) other;
        return this.equals(m) && internalCode.equals(m.internalCode)
                && serialNumber.equals(m.serialNumber) 
                && description.equals(m.description)
                && dateOfInstallation.equals(m.dateOfInstallation)
                && brand.equals(m.brand)
                && model.equals(m.model)
                && protocol.equals(m.protocol)
                && followedByMachine.equals(m.followedByMachine)
                && machineState.equals(m.machineState);
    }

    @Override
    public InternalCode identity() {
        return internalCode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.internalCode);
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
        final Machine other = (Machine) obj;
        return Objects.equals(this.internalCode, other.internalCode);
    }

    @Override
    public String toString() {
        return "Machine[" + internalCode + ']';
    }

    
}
