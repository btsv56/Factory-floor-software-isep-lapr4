package eapli.base.persistence.impl.jpa;

import eapli.base.productionsheetmanagement.domain.ProductionSheetLineProduct;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineProductRepository;


public class JpaProductionSheetLineProductRepository extends BasepaRepositoryBase<ProductionSheetLineProduct,Integer,Integer> implements ProductionSheetLineProductRepository {
    public JpaProductionSheetLineProductRepository() {
        super("id");
    }
}
