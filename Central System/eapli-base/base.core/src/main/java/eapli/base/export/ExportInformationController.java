/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export;

import eapli.base.export.xml.XMLFileValidator;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.framework.application.Controller;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import java.io.IOException;

/**
 *
 * @author Utilizador
 */
public class ExportInformationController implements Controller {

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final InformationExporterFactory factory = new InformationExporterFactory();
    private InformationExporter exporter;

    public void exportInformation(final String format, final String fileName) throws IOException {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.PRODUCTION_MANAGER);
        exporter = factory.build(format);
        exporter.defineFilePrinter("./xml/"+fileName);
    }

    public void exportRawMaterialCategories() {
        exporter.exportRawMaterialCategories();
    }

    public void exportRawMaterials() {
        exporter.exportRawMaterials();
    }

    public void exportProducts() {
        exporter.exportProducts();
    }

    public void exportDeposits() {
        exporter.exportDeposits();
    }

    public void exportMachines() {
        exporter.exportMachines();
    }

    public void exportProductionSheets() {
        exporter.exportProductionSheets();
    }

    public void exportProductionOrders() {
        exporter.exportProductionOrders();
    }

    public void exportLots() {
        exporter.exportLots();
    }

    public void exportProductionLines() {
        exporter.exportProductionLines();
    }

    public void exportIntake() {
        exporter.exportIntake();
    }

    public void exportEndFile() {
        exporter.exportEndFile();

    }
    
    public void exportWaste(){
        exporter.exportWastes();
    }
    
    public void exportEffectiveTimes(){
        exporter.exportEffectiveTime();
    }
    
    public void exportBruteTime(){
        exporter.exportBruteTime();
    }
    
    public boolean validateFile(String fileName) {
        return new XMLFileValidator().validateFile("./xml/"+fileName);
    }
}
