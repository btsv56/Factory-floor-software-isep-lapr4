package eapli.base.persistence.impl.jpa;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.framework.domain.model.general.Designation;

/**
 *
 * @author joaol
 */
public class JpaDepositRepository extends BasepaRepositoryBase<Deposit,Designation,Designation> implements DepositRepository {
    public JpaDepositRepository() {
        super("internalCode");
    }
}
