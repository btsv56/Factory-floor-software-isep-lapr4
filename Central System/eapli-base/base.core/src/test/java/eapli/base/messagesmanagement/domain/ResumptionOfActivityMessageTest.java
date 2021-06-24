/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.framework.domain.model.general.Description;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import org.junit.Test;

/**
 *
 * @author Utilizador
 */
public class ResumptionOfActivityMessageTest {

    //Machine
    private static final InternalCode INTERNAL_CODE = new InternalCode("1000");
    private static final SerialNumber SERIAL_NUMBER = new SerialNumber("1AAA");
    private static final Description DESCRIPTION = Description.valueOf("Machine that cuts things");
    private static final InstallationDate DATE_OF_INSTALATION = new InstallationDate(Calendar.getInstance());
    private static final Brand BRAND = new Brand("Mercedes");
    private static final Model MODEL = new Model("Mak");
    private static final MachineState STATE = new MachineState("ativo");
    private static final Protocol PROTOCOL = new Protocol(32);
    private static final Machine MACHINE = new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION, DATE_OF_INSTALATION, BRAND, MODEL, STATE, PROTOCOL);
    //Type of Message
    private static final Description TYPE_OF_MESSAGE = Description.valueOf("S1");
    //Date and hour
    private static final LocalDateTime DATEHOUR = LocalDateTime.of(2010, Month.MARCH, 1, 10, 10);
    //Error
    private static final Description ERROR = Description.valueOf(("Error"));

    @Test
    public void ensureResumptionOfActivityMessageCanBeCreated() {
        new ResumptionOfActivityMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, ERROR);
        assert (true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureResumptionOfActivityMessageMustHaveError() {
        new ResumptionOfActivityMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, null);
    }
}
