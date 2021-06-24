package eapli.base.infrastructure.bootstrapers;

import eapli.base.factoryfloormanagementarea.domain.InternalCode;

public final class TestDataConstants {

    //Raw material categories
    public static final String RAW_MATERIAL_CATEGORY_WOOD = "wood";
    public static final String RAW_MATERIAL_CATEGORY_PLASTIC = "plastic";
    public static final String RAW_MATERIAL_CATEGORY_METAL = "metal";

    //Raw material
    public static final String RAW_MATERIAL_1 = "raw_material_1";
    public static final String RAW_MATERIAL_2 = "raw_material_2";
    public static final String RAW_MATERIAL_3 = "raw_material_3";
    public static final String RAW_MATERIAL_4 = "raw_material_4";
    public static final String RAW_MATERIAL_5 = "raw_material_5";
    public static final String RAW_MATERIAL_6 = "raw_material_6";
    
    //Machine
    public static final InternalCode MACHINE_1 = new InternalCode("1000");
    public static final InternalCode MACHINE_2 = new InternalCode("1001");
    public static final InternalCode MACHINE_3 = new InternalCode("1002");
    public static final InternalCode MACHINE_4 = new InternalCode("1003");
    public static final InternalCode MACHINE_5 = new InternalCode("1004");
    public static final InternalCode MACHINE_6 = new InternalCode("1005");
    
    //Product
    public static final String PRODUCT_1 = "1000";
    public static final String PRODUCT_2 = "1001";
    public static final String PRODUCT_3 = "1002";
    public static final String PRODUCT_4 = "1003";
    public static final String PRODUCT_5 = "1004";

    //Production Sheets
    public static final String PRODUCTION_SHEET_1 = "ps1111";
    public static final String PRODUCTION_SHEET_2 = "ps1112";
    public static final String PRODUCTION_SHEET_3 = "ps1113";
    public static final String PRODUCTION_SHEET_4 = "ps1114";

    //Deposits
    public static final String DEPOSIT_1 = "depo_1";
    public static final String DEPOSIT_2 = "depo_2";
    public static final String DEPOSIT_3 = "depo_3";
    public static final String DEPOSIT_4 = "depo_4";
    public static final String DEPOSIT_5 = "depo_5";
    
    //Production Order
    public static final String PORDER_1 = "po1";

    //Production lines
    public static final String PLINE_1 = "pline_1";
    public static final String PLINE_2 = "pline_2";
    public static final String PLINE_3 = "pline_3";
    public static final String PLINE_4 = "pline_4";
    public static final String PLINE_5 = "pline_5";

    public static final String USER_TEST1 = "user1";

    @SuppressWarnings("squid:S2068")
    public static final String PASSWORD1 = "Password1";

    private TestDataConstants() {
        // ensure utility
    }
}
