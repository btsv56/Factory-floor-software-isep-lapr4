package eapli.base.persistence.impl.jpa;

import eapli.base.depositmanagement.domain.DepositProductSheet;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;

public class JpaDepositProductSheetRepository extends BasepaRepositoryBase<DepositProductSheet, Integer, Integer> implements DepositProductSheetRepository {
    public JpaDepositProductSheetRepository() {
        super("code");
    }
}
