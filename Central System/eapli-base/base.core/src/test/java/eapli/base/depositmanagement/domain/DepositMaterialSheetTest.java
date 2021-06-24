package eapli.base.depositmanagement.domain;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.framework.domain.model.general.Designation;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepositMaterialSheetTest {

    private static final RawMaterial MAT=new RawMaterial(Designation.valueOf("RawMaterial1"), "cork stopper", new RawMaterialCategory("wood", "woodstuff"), new TechnicalSheet("cork stopper", "..//files/technicalsheets/teste.pdf"));
    private static final int AMOUNT=1;

    @Test
    public void ensureDepositMaterialSheetWithCodeDepositMaterialAmount() {
        new DepositMaterialSheet(MAT);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveMaterial() {
        new DepositMaterialSheet(null);
    }

    @Test
    public void ensureAmountIsInitializedTo0() {
        DepositMaterialSheet dms=new DepositMaterialSheet(MAT);
        assertEquals(0, dms.getAmount());
    }

    @Test
    public void setAmountTest() {
        DepositMaterialSheet dms=new DepositMaterialSheet(MAT);
        dms.setAmount(AMOUNT);
        assertEquals(AMOUNT, dms.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void amountMeetsMinimumRequirementsTest() {
        DepositMaterialSheet dms=new DepositMaterialSheet(MAT);
        dms.setAmount(-1);
    }
}