package eapli.base.productionsheetmanagement.domain;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.framework.domain.model.general.Designation;

public class ProductionSheetTest {

    private static final Designation FABRICATION_CODE = Designation.valueOf("1234567890abcde");
    private static final String COMERCIAL_CODE = "1234567890abcde";
    private static final String BRIEF_DESCRIPTION = "Parafuso";
    private static final String COMPLETE_DESCRIPTION = "Parafuso";
    private static final String CATEGORY = "ME-PS";
    private static final String UNITY = "UN";
    private static final Product PRODUCT = new Product(FABRICATION_CODE, COMERCIAL_CODE, BRIEF_DESCRIPTION, COMPLETE_DESCRIPTION, CATEGORY, UNITY);

    private static final RawMaterialCategory CATEGORY_WOOD = new RawMaterialCategory("cork", "cork");
    private static final TechnicalSheet TECHNICAL_SHEET = new TechnicalSheet("cork stopper", "..//files/technicalsheets/teste.pdf");
    private static final String DESCRIPTION = "cork stopper";
    private static final Designation INTERNAL_CODE = Designation.valueOf("RawMaterial1");

    private static final RawMaterial RAW_MATERIAL = new RawMaterial(INTERNAL_CODE, DESCRIPTION, CATEGORY_WOOD, TECHNICAL_SHEET);

    private static final Designation PRODUCTION_SHEET_ID = Designation.valueOf("1234567890abcde");
    private static List<ProductionSheetLineProduct> productionSheetLineProducts = new ArrayList<>();
    private static List<ProductionSheetLineRawMaterial> productionSheetLineMats = new ArrayList<>();
    private static ProductionSheetLineProduct PRODUCTION_SHEET_LINE = new ProductionSheetLineProduct(PRODUCT, 4);
    private static ProductionSheetLineRawMaterial PRODUCTION_SHEET_LINE_MAT = new ProductionSheetLineRawMaterial(RAW_MATERIAL, 2);


    @Test
    public void ensureCreation(){
        productionSheetLineProducts.add(PRODUCTION_SHEET_LINE);
        productionSheetLineMats.add(PRODUCTION_SHEET_LINE_MAT);
        new ProductionSheet(PRODUCTION_SHEET_ID, productionSheetLineProducts,productionSheetLineMats);
        assertTrue(true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductionSheetIDCantBeEmpty(){
        productionSheetLineProducts.add(PRODUCTION_SHEET_LINE);
        productionSheetLineMats.add(PRODUCTION_SHEET_LINE_MAT);
        new ProductionSheet(Designation.valueOf(""), productionSheetLineProducts,productionSheetLineMats);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ensureProductionSheetIDCantBeNull(){
        productionSheetLineProducts.add(PRODUCTION_SHEET_LINE);
        productionSheetLineMats.add(PRODUCTION_SHEET_LINE_MAT);
        new ProductionSheet(null, productionSheetLineProducts,productionSheetLineMats);
    }

}