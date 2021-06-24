package eapli.base.productionordermanagement.domain;

import eapli.framework.domain.model.general.Designation;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductionOrderTest {

    private static final Designation CODE=Designation.valueOf("1");
    private static final String DESC="Production Order 1";

    @Test
    public void ensureProductionOrderWithCodeDesc() {
        new ProductionOrder(CODE, DESC);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCode() {
        new ProductionOrder(null, DESC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveDesc() {
        new ProductionOrder(CODE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescMustNotBeEmpty() {
        new ProductionOrder(CODE, "");
    }

}