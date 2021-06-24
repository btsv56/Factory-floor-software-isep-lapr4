package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
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
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public RawMaterialCategoryRepository rawMaterialCategory() {
        return new JpaRawMaterialCategoryRepository();
    }

    @Override
    public RawMaterialRepository rawMaterial() {
        return new JpaRawMaterialRepository();
    }

    @Override
    public DepositRepository deposit() {
        return new JpaDepositRepository();
    }

    @Override
    public DepositMaterialSheetRepository depositMaterialSheet() {
        return new JpaDepositMaterialSheetRepository();
    }

    @Override
    public DepositProductSheetRepository depositProductSheet() {
        return new JpaDepositProductSheetRepository();
    }

    @Override
    public MachineRepository machine() {
        return new JpaMachineRepository();
    }
    
    @Override
    public ProductRepository products() {
        return new JpaProductRepository();
    }

    @Override
    public ProductionSheetRepository productionSheets() {
        return new JpaProductionSheetRepository();
    }

    @Override
    public ProductionSheetLineProductRepository productionSheetLinesProducts() {
        return new JpaProductionSheetLineProductRepository();
    }

    @Override
    public ProductionSheetLineRawMaterialRepository productionSheetLinesMaterials() {
        return new JpaProductionSheetLineRawMaterialRepository();
    }

    @Override
    public ProductionLineRepository productionLine() {
        return new JpaProductionLineRepository();
    }

    @Override
    public ProductionOrderRepository productionOrder() {
        return new JpaProductionOrderRepository();
    }

    @Override
    public ProductionResultRepository productionResult() {
        return new JpaProductionResultRepository();
    }

    @Override
    public MessageRepository messages() {
        return new JpaMessageRepository();
    }

    @Override
    public ErrorNotificationRepository errornotification() {
        return new JpaErrorNotificationRepository();
    }


}
