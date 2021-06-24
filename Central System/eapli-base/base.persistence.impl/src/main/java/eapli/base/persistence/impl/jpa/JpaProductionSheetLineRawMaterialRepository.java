package eapli.base.persistence.impl.jpa;

import eapli.base.productionsheetmanagement.domain.ProductionSheetLineRawMaterial;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineRawMaterialRepository;


public class JpaProductionSheetLineRawMaterialRepository extends BasepaRepositoryBase<ProductionSheetLineRawMaterial,Integer,Integer> implements ProductionSheetLineRawMaterialRepository {
    public JpaProductionSheetLineRawMaterialRepository() {
        super("id");
    }
}
