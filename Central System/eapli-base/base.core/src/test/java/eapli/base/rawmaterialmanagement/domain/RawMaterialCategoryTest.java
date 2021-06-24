/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.domain;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Utilizador
 */
public class RawMaterialCategoryTest {

    @Test
    public void ensureRawMaterialCategorytestWithNameDescription() {
        new RawMaterialCategory("wood", "wood");
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRawMaterialCategoryNameMustNotBeNull() {
        new RawMaterialCategory(null, "wood");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRawMaterialCategoryNameMustNotBeEmpty() {
        new RawMaterialCategory("", "wood");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureRawMaterialCategoryNameCantHaveMore10Characters() {
        new RawMaterialCategory("12345678901", "wood");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRawMaterialCategoryDescriptionMustNotBeNull() {
        new RawMaterialCategory("wood", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureRawMaterialCategoryDescriptionMustNotBeEmpty() {
        new RawMaterialCategory("wood", "");
    }

}
