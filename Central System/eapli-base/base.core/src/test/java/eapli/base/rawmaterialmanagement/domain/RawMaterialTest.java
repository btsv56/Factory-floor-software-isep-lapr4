/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.domain;

import eapli.framework.domain.model.general.Designation;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Utilizador
 */
public class RawMaterialTest {

    private static final RawMaterialCategory CATEGORY_WOOD = new RawMaterialCategory("cork", "cork");
    private static final TechnicalSheet TECHNICAL_SHEET = new TechnicalSheet("cork stopper", "..//files/technicalsheets/teste.pdf");
    private static final String DESCRIPTION = "cork stopper";
    private static final Designation INTERNAL_CODE = Designation.valueOf("RawMaterial1");

    @Test
    public void ensureRawMaterialWithInternalCodeDescriptionCategoryTechnicalSheet() {
        new RawMaterial(INTERNAL_CODE, DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);
        assertTrue(true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureInternalCodeCantHaveMoreThan15Characters() {
        new RawMaterial(Designation.valueOf("1234567890123456"), DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureInternalCodeCantBeEmpty() {
        new RawMaterial(Designation.valueOf(""), DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveInternalCode() {
        new RawMaterial(null, DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveCategory() {
        new RawMaterial(INTERNAL_CODE, DESCRIPTION, null, TECHNICAL_SHEET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureMustHaveTechnicalSheet() {
        new RawMaterial(INTERNAL_CODE, DESCRIPTION, CATEGORY_WOOD, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionMustNotBeNull() {
        new RawMaterial(INTERNAL_CODE, null, CATEGORY_WOOD, TECHNICAL_SHEET);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureDescriptionMustNotBeEmpty() {
        new RawMaterial(INTERNAL_CODE, "", CATEGORY_WOOD, TECHNICAL_SHEET);
    }

}
