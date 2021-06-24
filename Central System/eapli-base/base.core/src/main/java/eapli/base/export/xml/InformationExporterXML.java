/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export.xml;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.repositories.DepositRepository;
import eapli.base.export.InformationExporter;
import eapli.base.factoryfloormanagementarea.repository.MachineRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;
import eapli.base.productionlinemanagement.repositories.ProductionLineRepository;
import eapli.base.productionordermanagement.domain.Lot;
import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.repositories.ProductionOrderRepository;
import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.repositories.ProductionResultRepository;
import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.repositories.ProductionSheetRepository;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialCategoryRepository;
import eapli.base.rawmaterialmanagement.repositories.RawMaterialRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Utilizador
 */
public class InformationExporterXML implements InformationExporter {

    PrintWriter stream;

    /**
     *
     * @param filename
     * @throws IOException
     */
    @Override
    public void defineFilePrinter(final String filename) throws IOException {
        stream = new PrintWriter(new FileWriter(filename));
        stream.println("<SSFM>");
    }

    @Override
    public void exportRawMaterialCategories() {
        final RawMaterialCategoryRepository rawMaterialCategoryRepository = PersistenceContext.repositories().rawMaterialCategory();
        final Iterable<RawMaterialCategory> rawMaterialCategories = rawMaterialCategoryRepository.findAll();
        new RawMaterialCategoryXMLExporter().export(rawMaterialCategories, stream);
    }

    @Override
    public void exportRawMaterials() {
        final RawMaterialRepository rawMaterialRepository = PersistenceContext.repositories().rawMaterial();
        final Iterable<RawMaterial> rawMaterials = rawMaterialRepository.findAll();
        new RawMaterialXMLExporter().export(rawMaterials, stream);
    }

    @Override
    public void exportProducts() {
        final ProductRepository productRepository = PersistenceContext.repositories().products();
        final Iterable<Product> products = productRepository.findAll();
        new ProductXMLExporter().export(products, stream);
    }

    @Override
    public void exportDeposits() {
        final DepositRepository depositRepository = PersistenceContext.repositories().deposit();
        final Iterable<Deposit> deposits = depositRepository.findAll();
        new DepositXMLExporter().export(deposits, stream);
    }

    @Override
    public void exportMachines() {
        final MachineRepository repository = PersistenceContext.repositories().machine();
        final Iterable<Machine> list = repository.findAll();
        new MachineXMLExporter().export(list, stream);
    }

    @Override
    public void exportProductionSheets() {
        final ProductionSheetRepository productionSheetRepository = PersistenceContext.repositories().productionSheets();
        final Iterable<ProductionSheet> productionSheets = productionSheetRepository.findAll();
        new ProductionSheetXMLExporter().export(productionSheets, stream);
    }

    @Override
    public void exportProductionOrders() {
        final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrder();
        final Iterable<ProductionOrder> productionOrders = productionOrderRepository.findAll();
        new ProductionOrderXMLExporter().export(productionOrders, stream);
    }

    @Override
    public void exportLots() {
        final ProductionOrderRepository productionOrderRepository = PersistenceContext.repositories().productionOrder();
        final Iterable<ProductionOrder> productionOrders = productionOrderRepository.findAll();
        List<Lot> lotsList=new ArrayList<>();
        for (ProductionOrder po : productionOrders) {
            if (!lotsList.contains(po.getLot())) lotsList.add(po.getLot());
        }
        Iterable<Lot> lots=getIterableFromIterator(lotsList.iterator());
        new LotXMLExporter().export(lots, stream);
    }

    @Override
    public void exportProductionLines() {
        final ProductionLineRepository productionLineRepository = PersistenceContext.repositories().productionLine();
        final Iterable<ProductionLine> productionLines = productionLineRepository.findAll();
        new ProductionLineXMLExporter().export(productionLines, stream);
    }

    @Override
    public void exportIntake() {
        final ProductionResultRepository productionResultRepository=PersistenceContext.repositories().productionResult();
        final Iterable<ProductionResult> productionsResults= productionResultRepository.findAll();
        if(productionsResults.iterator().hasNext()){
            new IntakeXMLExporter().export(productionsResults, stream);
        }
    }

    @Override
    public void exportEndFile() {
        stream.println("</SSFM>");
        stream.close();
    }

    public static <T> Iterable<T>
    getIterableFromIterator(Iterator<T> iterator) {
        return () -> iterator;
    }

    @Override
    public void exportWastes() {
        final ProductionResultRepository repository = PersistenceContext.repositories().productionResult();
        final Iterable<ProductionResult> list = repository.findAll();
        new WastesXMLExporter().export(list, stream);
    }

    @Override
    public void exportEffectiveTime() {
        final ProductionResultRepository repository = PersistenceContext.repositories().productionResult();
        final Iterable<ProductionResult> list = repository.findAll();
        new EffectiveTimeXMLExporter().export(list, stream);
    }

    @Override
    public void exportBruteTime() {
        final ProductionResultRepository repository = PersistenceContext.repositories().productionResult();
        final Iterable<ProductionResult> list = repository.findAll();
        new BruteTimeXMLExporter().export(list, stream);
    }
}
