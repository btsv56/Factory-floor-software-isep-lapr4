package eapli.base.depositmanagement.application;

import eapli.base.depositmanagement.domain.Deposit;

public class ListDepositController {

    final ListDepositService svc= new ListDepositService();

    /**
     * All deposits
     * @return all deposits
     */
    public Iterable<Deposit> allDeposits(){
        return this.svc.allDeposits();
    }

}
