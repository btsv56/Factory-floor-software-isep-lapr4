# [2-1-1007] - Machine's bootstrap

# 1. Requirements

**As a Project Manager**:

-  I intend for the team to boot (bootstrap) some of the machines.

According to my interpretation, this UC aims to initialize some data from scratch (in this case machines) so that the application can have test data, without being manually entered.

**Correlation/Dependecies:**

- [[2-1-3001] - Add new machines to the system](AddNewMachinesToTheSystem.md)
- [[2-2-1008] - Line production intialization (bootstrap)](../1180725/LineProductionBootstrap.md)

# 2. Analyze

#### **Statement**

- The machines are organized sequentially on production lines.
- Machine specification: a machine has an internal code, a serial number, a description, date of installation, a brand and model.
- There must be a possibility to associate one or more configuration files to the machine complemented with a brief description.

# 3. Design

The structur and design used in these functionality are the same as the other Bootstrappers.

## 3.1. Tests 

There's only one Smoke Test to test the CRUD.

# 4. Implementation

```java
public class MachineBootstrapper implements Action {
    
    private static final Logger LOGGER = LogManager.getLogger(MachineBootstrapper.class);
    
    private final MachineRepository repository = PersistenceContext.repositories().machine();

    @Override
    public boolean execute() {
        register(MACHINE_1, new SerialNumber("1A"), Description.valueOf("Máquina 1"), Calendars.of(2007,3,5), new Brand("Pops"), new Model("Manu"), null, new MachineState("stanby"));
        register(MACHINE_2, new SerialNumber("1B"), Description.valueOf("Máquina 2"), Calendars.of(2007,3,5), new Brand("Pops"), new Model("Manu"), MACHINE_1, new MachineState("stanby"));
        register(MACHINE_3, new SerialNumber("1C"), Description.valueOf("Máquina 3"), Calendars.of(2007,3,5), new Brand("Pops"), new Model("Manu"), MACHINE_2, new MachineState("stanby"));
        register(MACHINE_4, new SerialNumber("1D"), Description.valueOf("Máquina 4"), Calendars.of(2007,3,5), new Brand("Pops"), new Model("Manu"), MACHINE_3, new MachineState("stanby"));
        register(MACHINE_5, new SerialNumber("1E"), Description.valueOf("Máquina 5"), Calendars.of(2007,3,5), new Brand("Pops"), new Model("Manu"), MACHINE_4, new MachineState("stanby"));
    
        return true;
    }
    
    private void register(final InternalCode internalCode, final SerialNumber serialNumber, final Description description,
            final Calendar dateOfInstallation, final Brand brand, final Model model, final InternalCode followedByMachine,
            final MachineState machineState) {
        final SpecifyMachineController controller = new SpecifyMachineController();
        try {
            controller.specifyMachine(internalCode, serialNumber, description, dateOfInstallation, brand, model, followedByMachine, machineState);
        } catch (final IntegrityViolationException | ConcurrencyException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", description);
            LOGGER.trace("Assuming existing record", e);
        }
    }
    
}
```



# 5. Integration/Demonstration

It was necessary to integrate the functionality of specifying machines in order to develop this functionality.

# 6. Observations

None.



