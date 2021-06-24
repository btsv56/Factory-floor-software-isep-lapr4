/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

import org.junit.Test;

import eapli.base.factoryfloormanagementarea.domain.Brand;
import eapli.base.factoryfloormanagementarea.domain.InstallationDate;
import eapli.base.factoryfloormanagementarea.domain.InternalCode;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.factoryfloormanagementarea.domain.MachineState;
import eapli.base.factoryfloormanagementarea.domain.Model;
import eapli.base.factoryfloormanagementarea.domain.Protocol;
import eapli.base.factoryfloormanagementarea.domain.SerialNumber;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;

/**
 *
 * @author Utilizador
 */
public class StartOfActivityMessageTest {

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
    private static final Description TYPE_OF_MESSAGE = Description.valueOf("S0");
    //Date and hour
    private static final LocalDateTime DATEHOUR = LocalDateTime.of(2010, Month.MARCH, 1, 10, 10);
    //ProductionOrder
    private static final Designation CODE = Designation.valueOf("1");
    private static final String DESC = "Production Order 1";
    private static final ProductionOrder PRODUCTION_ORDER = new ProductionOrder(CODE, DESC);

    @Test
    public void ensureStartOfActivityMessageCanBeCreated() {
        new StartOfActivityMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCTION_ORDER);
        assertTrue(true);
    }

    @Test
    public void ensureStartOfActivityMessageMustHaveProductionOrder() {
        new StartOfActivityMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, null);
    }
}
