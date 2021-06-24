package eapli.base.persistence.impl.jpa;

import eapli.base.depositmanagement.domain.DepositMaterialSheet;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;

public class JpaDepositMaterialSheetRepository extends BasepaRepositoryBase<DepositMaterialSheet, Integer, Integer> implements DepositMaterialSheetRepository {
    public JpaDepositMaterialSheetRepository() {
        super("internalCode");
    }
}
