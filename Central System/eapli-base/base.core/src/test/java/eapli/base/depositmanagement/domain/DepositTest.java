package eapli.base.depositmanagement.domain;

import eapli.framework.domain.model.general.Designation;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepositTest {

    private static final Designation CODE=Designation.valueOf("1");
    private static final String DESC="1st Deposit";

    @Test
    public void ensureDepositWithCodeDesc() {
        new Deposit(CODE, DESC);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCode() {
        new Deposit(null, DESC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDesc() {
        new Deposit(CODE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescMustNotBeEmpty() {
        new Deposit(CODE, "");
    }

}