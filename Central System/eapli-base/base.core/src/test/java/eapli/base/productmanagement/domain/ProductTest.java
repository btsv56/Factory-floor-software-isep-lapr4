package eapli.base.productmanagement.domain;

import eapli.framework.domain.model.general.Designation;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProductTest {

    private static final Designation FABRICATION_CODE = Designation.valueOf("1234567890abcde");
    private static final String COMERCIAL_CODE = "1234567890abcde";
    private static final String BRIEF_DESCRIPTION = "Parafuso";
    private static final String COMPLETE_DESCRIPTION = "Parafuso";
    private static final String CATEGORY = "ME-PS";
    private static final String UNITY = "UN";

    @Test
    public void ensureProductCreaetionWorks(){
        Product p = new Product(FABRICATION_CODE,COMERCIAL_CODE,BRIEF_DESCRIPTION,COMPLETE_DESCRIPTION,CATEGORY,UNITY);
        System.out.println(p);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCompleteDescriptionIsNotEmpty(){
        new Product(FABRICATION_CODE, COMERCIAL_CODE, BRIEF_DESCRIPTION, "", CATEGORY, UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureBriefDescriptionIsNotEmpty(){
        new Product(FABRICATION_CODE, COMERCIAL_CODE, "", COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureUnityIsNotEmpty(){
        new Product(FABRICATION_CODE, COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureCategoryIsNotEmpty(){
        new Product(FABRICATION_CODE, COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, "", UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureComercialCodeMustNotBeEmpty() {
        new Product(FABRICATION_CODE, "", BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureFabricationCodeMustNotBeEmpty() {
        new Product(Designation.valueOf(""), COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureComercialCodeCantHaveMoreThan15Characters() {
        new Product(FABRICATION_CODE, "", BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureFabricationCodeCantHaveMoreThan15Characters() {
        new Product(Designation.valueOf("1234567890123456"), COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);
    }

}