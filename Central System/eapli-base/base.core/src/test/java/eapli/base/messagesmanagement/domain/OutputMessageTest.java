/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

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
import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
/**
 *
 * @author Utilizador
 */
public class OutputMessageTest {

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
    //Product
    private static final Designation FABRICATION_CODE = Designation.valueOf("1234567890abcde");
    private static final String COMERCIAL_CODE = "1234567890abcde";
    private static final String BRIEF_DESCRIPTION = "Parafuso";
    private static final String COMPLETE_DESCRIPTION = "Parafuso";
    private static final String CATEGORY = "ME-PS";
    private static final String UNITY = "UN";
    private static final Product PRODUCT = new Product(FABRICATION_CODE, COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    //Quantity
    private static final int QUANTITY = 10;
    //ProductionOrder
    private static final Designation CODE = Designation.valueOf("1");
    private static final String DESC = "Production Order 1";
    private static final ProductionOrder PRODUCTION_ORDER = new ProductionOrder(CODE, DESC);

    @Test
    public void ensureOutputMessageCanBeCreated() {
        new OutputMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, QUANTITY, PRODUCTION_ORDER);
        assert (true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureOutputMessageMustHaveProduct() {
        new OutputMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, null, QUANTITY, PRODUCTION_ORDER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureOutputMessageMustHaveQuantity() {
        new OutputMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, -1, PRODUCTION_ORDER);
    }

    @Test
    public void ensureOutputMessageCanBeCreatedWithoutProductionOrder() {
        new OutputMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, QUANTITY, null);
    }

}
