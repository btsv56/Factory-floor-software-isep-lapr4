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
public class TechnicalSheetTest {

    private static final String NAME = "wood";
    private static final String PATH = "..//files/technicalsheets/teste.pdf";

    @Test
    public void ensureTechnicalSheetWithNamePath() {
        new TechnicalSheet(NAME, PATH);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTechnicalSheetNameMutNotBeNull() {
        new TechnicalSheet(null, PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTechinicalSheetNameMustNotBeEmpty() {
        new TechnicalSheet("", PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTechnicalSheetPathMustNotBeNull() {
        new TechnicalSheet(NAME, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureTechnicalSheetPathMustNotBeEmpty() {
        new TechnicalSheet(NAME, "");
    }

}
