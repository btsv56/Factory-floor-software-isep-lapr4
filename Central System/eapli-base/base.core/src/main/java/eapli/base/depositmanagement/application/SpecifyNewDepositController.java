package eapli.base.depositmanagement.application;

import eapli.base.depositmanagement.domain.DepositMaterialSheet;
import eapli.base.depositmanagement.domain.DepositProductSheet;
import eapli.base.depositmanagement.repositories.DepositMaterialSheetRepository;
import eapli.base.depositmanagement.repositories.DepositProductSheetRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.productmanagement.application.ListProductsController;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.application.ListRawMaterialService;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

import java.util.ArrayList;

/**
 *
 * @author joaol
 */
public class SpecifyNewDepositController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final DepositRepository repository = PersistenceContext.repositories().deposit();
    private final ListRawMaterialService rmcs= new ListRawMaterialService();
    private final ListProductsController pcs= new ListProductsController();
    ArrayList<DepositMaterialSheet> matSheets=new ArrayList<>();
    ArrayList<DepositProductSheet> prodSheets=new ArrayList<>();
    private Deposit depo;
    /**
     * Controller to specify a new deposit
     *
     * @param code: identification code
     * @param desc: description
     * @return new deposit
     */
    public void specifyNewDeposit(String code, String desc) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.FACTORY_FLOOR_MANAGER);
        depo= new Deposit(Designation.valueOf(code), desc);
    }

    public Iterable<RawMaterial> getMaterials() {
        return this.rmcs.allRawMaterials();
    }

    public Iterable<Product> getProducts() {
        return this.pcs.getProducts();
    }

    public boolean addDepositMaterialSheet(RawMaterial mat) {
        DepositMaterialSheet matSheet=new DepositMaterialSheet(mat);
        if (depo.addMaterial(matSheet)) {
            return matSheets.add(matSheet);
        }
        return false;
    }

    public boolean addDepositProductSheet(Product prod) {
        DepositProductSheet prodSheet=new DepositProductSheet(prod);
        if (depo.addProduct(prodSheet)) {
            return prodSheets.add(prodSheet);
        }
        return false;
    }

    public Deposit save() {
        if (!matSheets.isEmpty()) {
            DepositMaterialSheetRepository matSheetRepository = PersistenceContext.repositories().depositMaterialSheet();
            for (DepositMaterialSheet matSheet : matSheets) {
                matSheetRepository.save(matSheet);
            }
        }
        if (!prodSheets.isEmpty()) {
            DepositProductSheetRepository productSheetRepository = PersistenceContext.repositories().depositProductSheet();
            for (DepositProductSheet prodSheet : prodSheets) {
                productSheetRepository.save(prodSheet);
            }
        }
        this.repository.save(depo);
        return depo;
    }
}
