/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.messagesmanagement.domain;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.factoryfloormanagementarea.domain.*;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.framework.domain.model.general.Description;
import eapli.framework.domain.model.general.Designation;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import org.junit.Test;
/**
 *
 * @author Utilizador
 */
public class ReversalMessageTest {

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
    //RawMaterial
    private static final RawMaterialCategory CATEGORY_WOOD = new RawMaterialCategory("cork", "cork");
    private static final TechnicalSheet TECHNICAL_SHEET = new TechnicalSheet("cork stopper", "..//files/technicalsheets/teste.pdf");
    private static final RawMaterial RAW_MATERIAL = new RawMaterial(FABRICATION_CODE, BRIEF_DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);
    //Quantity
    private static final int QUANTITY = 10;
    //Deposit
    private static final Designation CODE = Designation.valueOf("1");
    private static final String DESC = "1st Deposit";
    private static final Deposit DEPOSIT = new Deposit(CODE, DESC);

    @Test
    public void ensureReversalMessageCanBeCreatedWithProduct() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, QUANTITY, DEPOSIT);
        assert (true);
    }

    @Test
    public void ensureReversalMessageCanBeCreatedWithRawMaterial() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, RAW_MATERIAL, QUANTITY, DEPOSIT);
        assert (true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveProduct() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, (Product) null, QUANTITY, DEPOSIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveRawMaterial() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, (RawMaterial) null, QUANTITY, DEPOSIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveQuantityForRawMaterial() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, RAW_MATERIAL, -1, DEPOSIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveQuantityForProduct() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, -1, DEPOSIT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveDepositForRawMaterial() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, RAW_MATERIAL, QUANTITY, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureReversalMessageMustHaveDepositForProduct() {
        new ReversalMessage(MACHINE, TYPE_OF_MESSAGE, DATEHOUR, PRODUCT, QUANTITY, null);
    }

}
