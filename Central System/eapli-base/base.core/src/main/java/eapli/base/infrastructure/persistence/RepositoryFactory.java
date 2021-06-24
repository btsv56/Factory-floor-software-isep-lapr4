/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;

import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.errornotificationmanagement.repository.ErrorNotificationRepository;
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

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

    /**
     * factory method to create a transactional context to use in the
     * repositories
     *
     * @return
     */
    TransactionalContext newTransactionalContext();

    /**
     *
     * @param autoTx the transactional context to enrol
     * @return
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    UserRepository users();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    ClientUserRepository clientUsers(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    ClientUserRepository clientUsers();

    /**
     *
     * @param autoTx the transactional context to enroll
     * @return
     */
    SignupRequestRepository signupRequests(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    SignupRequestRepository signupRequests();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    RawMaterialCategoryRepository rawMaterialCategory();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    RawMaterialRepository rawMaterial();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    DepositRepository deposit();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    DepositMaterialSheetRepository depositMaterialSheet();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    DepositProductSheetRepository depositProductSheet();

    /**
     * repository will be created in auto transaction mode
     *
     * @return
     */
    MachineRepository machine();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductRepository products();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionSheetRepository productionSheets();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionSheetLineProductRepository productionSheetLinesProducts();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionSheetLineRawMaterialRepository productionSheetLinesMaterials();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionLineRepository productionLine();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionOrderRepository productionOrder();

    /**
     * Repository will be created in auto transaction mode
     *
     * @return
     */
    ProductionResultRepository productionResult();

    /**
     * Message repository
     *
     * @return
     */
    MessageRepository messages();

    /**
     * Error notification repository
     * 
     * @return 
     */
    ErrorNotificationRepository errornotification();
}
