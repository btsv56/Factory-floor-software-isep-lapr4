package eapli.base.infrastructure.smoketests.backoffice;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineProduct;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineRawMaterial;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineProductRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetLineRawMaterialRepository;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.domain.TechnicalSheet;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import eapli.framework.domain.model.general.Designation;
import eapli.framework.validations.Invariants;

public class ProductionSheetCRUDSmokeTester {

    private static final Logger LOGGER = LogManager.getLogger(ProductionSheetCRUDSmokeTester.class);
    private final ProductionSheetRepository repo = PersistenceContext.repositories().productionSheets();
    private final RawMaterialCategoryRepository aux= PersistenceContext.repositories().rawMaterialCategory();
    private final RawMaterialRepository mat = PersistenceContext.repositories().rawMaterial();
    private final ProductRepository prod = PersistenceContext.repositories().products();
    private final ProductionSheetLineRawMaterialRepository psMat = PersistenceContext.repositories().productionSheetLinesMaterials();
    private final ProductionSheetLineProductRepository psProd = PersistenceContext.repositories().productionSheetLinesProducts();


    final private Designation ID1 = Designation.valueOf("one");
    final private Designation ID2 = Designation.valueOf("two");

    public void testProductCRUD() {

        aux.save(new RawMaterialCategory("first","first desc"));
        final RawMaterialCategory cat=aux.ofIdentity("first").orElseThrow(IllegalStateException::new);

        // save
        final Product p1 = new Product(Designation.valueOf("111"), "one", "aaa", "aaa", "aaa", "aaa");
        prod.save(p1);
        final Product p2 = new Product(Designation.valueOf("222"), "two", "aaa", "aaa", "aaa", "aaa");
        prod.save(p2);
        final RawMaterial m1 = new RawMaterial(Designation.valueOf("first"),"first desc",cat,new TechnicalSheet("first",".///files/technicalsheets/teste.pdf"));
        mat.save(m1);
        final RawMaterial m2 = new RawMaterial(Designation.valueOf("second"),"second desc",cat, new TechnicalSheet("secind",".///files/technicalsheets/teste.pdf"));
        mat.save(m2);
        final ProductionSheetLineProduct psl1 = new ProductionSheetLineProduct(p1, 2);
        psProd.save(psl1);
        final ProductionSheetLineProduct psl2 = new ProductionSheetLineProduct(p2,4);
        psProd.save(psl2);
        final ProductionSheetLineRawMaterial pslm1 = new ProductionSheetLineRawMaterial(m1, 2);
        psMat.save(pslm1);
        final ProductionSheetLineRawMaterial pslm2 = new ProductionSheetLineRawMaterial(m2, 4);
        psMat.save(pslm2);
        final List<ProductionSheetLineProduct> prods = new ArrayList<>();
        final List<ProductionSheetLineRawMaterial> mats = new ArrayList<>();
        prods.add(psl1);
        prods.add(psl2);
        mats.add(pslm1);
        mats.add(pslm2);
        final ProductionSheet ps1 = new ProductionSheet(ID1, prods, mats);
        final ProductionSheet ps2 = new ProductionSheet(ID2, prods, mats);
        repo.save(ps1);
        repo.save(ps2);
        p1.addProductionSheet(ps1);
        p2.addProductionSheet(ps2);
        prod.save(p1);
        prod.save(p2);
        LOGGER.info("»»» created production sheets");

        // findAll
        final Iterable<ProductionSheet> l = repo.findAll();
        Invariants.nonNull(l);
        Invariants.nonNull(l.iterator());
        Invariants.ensure(l.iterator().hasNext());
        LOGGER.info("»»» find all production sheets");

        // count
        final long n = repo.count();
        LOGGER.info("»»» # production sheets = {}", n);

        // ofIdentity
        final ProductionSheet o1 = repo.ofIdentity(ID1).orElseThrow(IllegalStateException::new);
        final ProductionSheet o2 = repo.ofIdentity(ID2).orElseThrow(IllegalStateException::new);
        LOGGER.info("»»» found production sheets of identity");

        // containsOfIdentity
        final boolean hasId = repo.containsOfIdentity(o1.identity());
        Invariants.ensure(hasId);
        LOGGER.info("»»» contains product of identity");

        // contains
        final boolean has = repo.contains(o1);
        Invariants.ensure(has);
        LOGGER.info("»»» contains product");

        // delete
        repo.delete(o1);

        LOGGER.info("»»» delete production sheets");

        // deleteOfIdentity
        repo.deleteOfIdentity(o2.identity());
        LOGGER.info("»»» delete production sheets of identity");

        psMat.delete(pslm1);
        psMat.delete(pslm2);
        psProd.delete(psl1);
        psProd.delete(psl2);
        prod.delete(p1);
        prod.delete(p2);
        mat.delete(m1);
        mat.delete(m2);

        // size
        final long n2 = repo.size();
        Invariants.ensure(n2 == n - 2);
        LOGGER.info("»»» # production sheets = {}", n2);
    }

}
