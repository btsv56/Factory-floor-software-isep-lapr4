package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
import eapli.base.infrastructure.bootstrapers.BaseBootstrapper;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.productionresultmanagement.repositories.ProductionResultRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineProductRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineRawMaterialRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.messagesmanagement.repositories.MessageRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.InMemoryUserRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return new InMemorySignupRequestRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

    @Override
    public RawMaterialRepository rawMaterial() {
        return new InMemoryRawMaterialRepository();
    }

    @Override
    public RawMaterialCategoryRepository rawMaterialCategory() {
        return new InMemoryRawMaterialCategoryRepository();
    }

    @Override
    public DepositRepository deposit() {
        return new InMemoryDepositRepository();
    }

    @Override
    public DepositMaterialSheetRepository depositMaterialSheet() {
        return new InMemoryDepositMaterialSheetRepository();
    }

    @Override
    public DepositProductSheetRepository depositProductSheet() {
        return new InMemoryDepositProductSheetRepository();
    }

    @Override
    public MachineRepository machine() {
        return new InMemoryMachineRepository();
    }
    @Override
    public ProductRepository products() {
        return new InMemoryProductRepository();
    }

    @Override
    public ProductionSheetRepository productionSheets(){
        return new InMemoryProductionSheetRepository();
    }

    @Override
    public ProductionSheetLineProductRepository productionSheetLinesProducts() {
        return new InMemoryProductionSheetLineProductRepository();
    }

    @Override
    public ProductionSheetLineRawMaterialRepository productionSheetLinesMaterials() {
        return new InMemoryProductionSheetLineRawMaterialRepository();
    }

    @Override
    public ProductionLineRepository productionLine() { return new InMemoryProductionLineRepository(); }

    @Override
    public ProductionOrderRepository productionOrder() { return new InMemoryProductionOrderRepository(); }

    @Override
    public ProductionResultRepository productionResult() { return new InMemoryProductionResultRepository(); }

    @Override
    public MessageRepository messages() {
        return new InMemoryMessageRepository();
    }

    @Override
    public ErrorNotificationRepository errornotification() {
        return new InMemoryErrorNotificationRepository();
    }

}
