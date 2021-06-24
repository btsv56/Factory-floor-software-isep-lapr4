/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.factoryfloormanagementarea.domain;

import eapli.framework.domain.model.general.Description;
import eapli.framework.util.Calendars;
import java.util.Calendar;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author bruno
 */
public class MachineTest {
    private static final InternalCode INTERNAL_CODE = new InternalCode("1000");
    private static final SerialNumber SERIAL_NUMBER = new SerialNumber("1AAA");
    private static final Description DESCRIPTION = Description.valueOf("Máquina de cortar coisas");
    private static final InstallationDate DATE_OF_INSTALATION = new InstallationDate(Calendar.getInstance());
    private static final Brand BRAND = new Brand("Mercedes");
    private static final Model MODEL = new Model("Mak");
    private static final MachineState STATE = new MachineState("ativo");
    private static final Protocol PROTOCOL = new Protocol(32);
    private static final Machine machine = new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);

    @Test(expected = IllegalArgumentException.class)
    public void ensureInstallationDateIsNotFromFuture(){
        Calendar tomorrow = Calendars.now();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, new InstallationDate(tomorrow), BRAND, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureConfigurationFileIsNotNull(){
        machine.attach(null);
    }
    
    @Test
    public void ensureMachine(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
        assertTrue(true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineInternalCodeNotNull(){
        new Machine(null, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineSerialNumberNotNull(){
        new Machine(INTERNAL_CODE, null, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineDescriptionNotNull(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, null, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineDateOfInstallationNotNull(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, null, BRAND, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineBrandNotNull(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, null, MODEL, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineModelNotNull(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, null, STATE, PROTOCOL);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMachineStateNotNull(){
        new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, null, PROTOCOL);
    }
    
    
}
