package eapli.base.depositmanagement.domain;

import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.model.general.Designation;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepositProductSheetTest {

    private static final Designation FABRICATION_CODE = Designation.valueOf("1234567890abcde");
    private static final String COMERCIAL_CODE = "1234567890abcde";
    private static final String BRIEF_DESCRIPTION = "Parafuso";
    private static final String COMPLETE_DESCRIPTION = "Parafuso";
    private static final String CATEGORY = "ME-PS";
    private static final String UNITY = "UN";
    private static final Product PRODUCT = new Product(FABRICATION_CODE,COMERCIAL_CODE,BRIEF_DESCRIPTION,COMPLETE_DESCRIPTION,CATEGORY,UNITY);
    private static final int AMOUNT=1;

    @Test
    public void ensureDepositProductSheetWithCodeDepositProductAmount() {
        new DepositProductSheet(PRODUCT);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveProduct() {
        new DepositProductSheet(null);
    }

    @Test
    public void ensureAmountIsInitializedTo0() {
        DepositProductSheet dms=new DepositProductSheet(PRODUCT);
        assertEquals(0, dms.getAmount());
    }

    @Test
    public void setAmountTest() {
        DepositProductSheet dms=new DepositProductSheet(PRODUCT);
        dms.setAmount(AMOUNT);
        assertEquals(AMOUNT, dms.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void amountMeetsMinimumRequirementsTest() {
        DepositProductSheet dms=new DepositProductSheet(PRODUCT);
        dms.setAmount(-1);
    }
}