package eapli.base.persistence.impl.jpa;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.framework.domain.model.general.Designation;

public class JpaProductionSheetRepository extends BasepaRepositoryBase<ProductionSheet, Designation, Designation> implements ProductionSheetRepository {

    public JpaProductionSheetRepository(){
        super("productionSheetID");
    }

}
