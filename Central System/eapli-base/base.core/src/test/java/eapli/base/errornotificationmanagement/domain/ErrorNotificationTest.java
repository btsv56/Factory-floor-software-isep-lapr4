/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.errornotificationmanagement.domain;

import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.framework.domain.model.general.Description;
import java.time.LocalDateTime;
import java.util.Calendar;
import org.junit.Test;

/**
 *
 * @author Utilizador
 */
public class ErrorNotificationTest {
    
    private static final InternalCode INTERNAL_CODE = new InternalCode("1000");
    private static final SerialNumber SERIAL_NUMBER = new SerialNumber("1AAA");
    private static final Description DESCRIPTION_MACHINE = Description.valueOf("Máquina de cortar coisas");
    private static final InstallationDate DATE_OF_INSTALATION = new InstallationDate(Calendar.getInstance());
    private static final Brand BRAND = new Brand("Mercedes");
    private static final Model MODEL = new Model("Mak");
    private static final MachineState STATE = new MachineState("ativo");
    private static final Machine MACHINE = new Machine(INTERNAL_CODE, SERIAL_NUMBER, DESCRIPTION_MACHINE, DATE_OF_INSTALATION, BRAND, MODEL, STATE, new Protocol(32));
    private static final Description DESCRIPTION= Description.valueOf("Invalid message");
    private static final ErrorNotificationType TYPE= new ErrorNotificationType(Description.valueOf("Product does not exit"));
    private static final Description MESSAGE_TYPE= Description.valueOf("S0");

    @Test
    public void ensureErrorNotificationCanBeCreated() {
        new ErrorNotification(MACHINE,DESCRIPTION, TYPE, MESSAGE_TYPE,LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureErrorNotificationCantBeCreatedWithoutDescription() {
        new ErrorNotification(MACHINE,null, TYPE, MESSAGE_TYPE,LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureErrorNotificationCantBeCreatedWithoutType() {
        new ErrorNotification(MACHINE,DESCRIPTION, null, MESSAGE_TYPE,LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureErrorNotificationCantBeCreatedWithoutMessageType() {
        new ErrorNotification(MACHINE,DESCRIPTION, TYPE, null,LocalDateTime.now());
    }

}
