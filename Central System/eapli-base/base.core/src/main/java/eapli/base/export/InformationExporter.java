/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export;

import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public interface InformationExporter {

    void exportRawMaterialCategories();

    void exportRawMaterials();

    void exportProducts();

    void exportDeposits();

    void exportMachines();

    void exportProductionSheets();

    void exportProductionOrders();

    void exportLots();

    void exportProductionLines();

    void exportIntake();

    void defineFilePrinter(String fileName) throws IOException;

    void exportEndFile();
    
    void exportWastes();
    
    void exportEffectiveTime();
    
    void exportBruteTime();
}
