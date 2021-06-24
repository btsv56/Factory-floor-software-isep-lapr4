package eapli.base.productionlinemanagement.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eapli.framework.domain.model.general.Designation;

public class ProductionLineTest {

    private final Designation CODE = Designation.valueOf("CODE");
    private final String DESC = "DESC";

    /*private static final InternalCode INTERNAL_CODE = new InternalCode("1000");
    private static final SerialNumber SERIAL_NUMBER = new SerialNumber("1AAA");
    private static final Description DESCRIPTION = Description.valueOf("Máquina de cortar coisas");
    private static final InstallationDate DATE_OF_INSTALATION = new InstallationDate(Calendar.getInstance());
    private static final Brand BRAND = new Brand("Mercedes");
    private static final Model MODEL = new Model("Mak");
    private static final MachineState STATE = new MachineState("ativo");
    private static final Protocol PROTOCOL = new Protocol(32);
    private static final Machine MACHINE = new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
    private final Machine FOLLOWED_BY_MACHINE = new Machine(new InternalCode("1001"), new SerialNumber("1AAB"), DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, new Protocol(33));*/


    @Test
    public void ensureProductionLineWithCodeDesc() {
        new ProductionLine(CODE, DESC);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCode() {
        new ProductionLine(null, DESC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDesc() {
        new ProductionLine(CODE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescMustNotBeEmpty() {
        new ProductionLine(CODE, "");
    }

}
