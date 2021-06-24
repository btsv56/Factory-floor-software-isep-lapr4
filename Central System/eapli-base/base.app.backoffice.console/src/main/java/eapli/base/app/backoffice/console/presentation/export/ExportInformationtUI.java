/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.app.backoffice.console.presentation.export;

import eapli.base.export.ExportInformationController;
import eapli.base.export.XSLTransformationController;
import eapli.framework.application.Controller;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Utilizador
 */
public class ExportInformationtUI extends AbstractUI {

    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String XHTML = "HTML";
    private static final String JSON = "Json";
    private static final String TEXT = "Text";
    private static final String XML = "XML";
    private static final String ALL = "All";

    private final ExportInformationController theController = new ExportInformationController();
    private final XSLTransformationController transformationController = new XSLTransformationController();

    protected Controller controller() {
        return this.theController;
    }

    @Override
    protected boolean doShow() {
        final List<String> options = new ArrayList<>();
        options.add(YES);
        options.add(NO);
        final SelectWidget<String> selector = new SelectWidget<>("Options:", options,
                new OptionsPrinter());
        String fileName = Console.readLine("File name: ");
        String format = findFormat(fileName); //Format is entered automatically
        try {
            theController.exportInformation(format, fileName);
        } catch (IOException ex) {
            System.out.println("Wrong fileName");
            return false;
        } catch (Exception e) {
            System.out.println("Wrong format");
            return false;
        }
        System.out.println("Do you wish to export all information");
        selector.show();
        String answer = selector.selectedElement();
        if (answer == null) {
            return false;
        }
        if (answeredYes(answer)) {
            exportAll();
        } else {
            int choice;
            final List<String> options2 = new ArrayList<>();
            options2.add("Raw Material Categories");
            options2.add("Raw Materials");
            options2.add("Products");
            options2.add("Deposits");
            options2.add("Machines");
            options2.add("Production sheets");
            options2.add("Lots");
            options2.add("Production orders");
            options2.add("Production lines");
            options2.add("Effective and real consumption");
            options2.add("Wastes");
            options2.add("Machine Effective Times");
            options2.add("Machine Brute Times");
            boolean[] flags = new boolean[13];
            final SelectWidget<String> selector2 = new SelectWidget<>("Options:", options2,
                    new OptionsPrinter());
            do {
                selector2.show();
                choice = selector2.selectedOption();
                switch (choice) {
                    case 1:
                        if (flags[0]) {
                            System.out.println("Raw Material Categories have already been exported.");
                            break;
                        }
                        theController.exportRawMaterialCategories();
                        flags[0] = true;
                        System.out.println("Raw Material categories exported.\n");
                        break;
                    case 2:
                        if (flags[1]) {
                            System.out.println("Raw Materials have already been exported.");
                            break;
                        } else if (!flags[0]) {
                            System.out.println("Raw Material categories must be exported first.\n");
                            break;
                        }
                        theController.exportRawMaterials();
                        flags[1] = true;
                        System.out.println("Raw Materials exported.\n");
                        break;
                    case 3:
                        if (flags[2]) {
                            System.out.println("Products have already been exported.");
                            break;
                        }
                        theController.exportProducts();
                        flags[2] = true;
                        System.out.println("Products exported.\n");
                        break;
                    case 4:
                        if (flags[3]) {
                            System.out.println("Deposits have already been exported.");
                            break;
                        } else if (!flags[1] || !flags[2]) {
                            System.out.println("Raw Materials and Products must be exported first.\n");
                            break;
                        }
                        theController.exportDeposits();
                        flags[3] = true;
                        System.out.println("Deposits exported.\n");
                        break;
                    case 5:
                        if (flags[4]) {
                            System.out.println("Machines have already been exported.");
                            break;
                        }
                        theController.exportMachines();
                        flags[4] = true;
                        System.out.println("Machines exported.\n");
                        break;
                    case 6:
                        if (flags[5]) {
                            System.out.println("Production sheets have already been exported.");
                            break;
                        } else if (!flags[1] || !flags[2]) {
                            System.out.println("Raw Materials and Products must be exported first.\n");
                            break;
                        }
                        theController.exportProductionSheets();
                        flags[5] = true;
                        System.out.println("Production sheets exported.\n");
                        break;
                    case 7:
                        if (flags[6]) {
                            System.out.println("Lots have already been exported.");
                            break;
                        }
                        theController.exportLots();
                        flags[6] = true;
                        System.out.println("Lots exported.\n");
                        break;
                    case 8:
                        if (flags[7]) {
                            System.out.println("Production orders have already been exported.");
                            break;
                        } else if (!flags[6] || !flags[5]) {
                            System.out.println("Lots and Production sheets must be exported first.\n");
                            break;
                        }
                        theController.exportProductionOrders();
                        flags[7] = true;
                        System.out.println("Production orders exported.\n");
                        break;
                    case 9:
                        if (flags[8]) {
                            System.out.println("Production lines have already been exported.");
                            break;
                        } else if (!flags[4]) {
                            System.out.println("Machines must be exported first.\n");
                            break;
                        }
                        theController.exportProductionLines();
                        flags[8] = true;
                        System.out.println("Production lines exported.\n");
                        break;
                    case 10:
                        if (flags[9]) {
                            System.out.println("Consumptions have already been exported.");
                            break;
                        } else if (!flags[1] || !flags[2] || !flags[3] || !flags[5] || !flags[7]) {
                            System.out.println("Raw materials, products, machines and deposits and production order must be exported first.\n");
                            break;
                        }
                        theController.exportIntake();
                        break;
                    case 11:
                        if (flags[10]) {
                            System.out.println("Wastes have already been exported.");
                            break;
                        } else if (!flags[1] || !flags[2] || !flags[4] || !flags[5] || !flags[7]) {
                            System.out.println("Raw materials, products, machines, deposits and production order must be exported first.\n");
                            break;
                        }
                        theController.exportWaste();
                        flags[10] = true;
                        System.out.println("Wastes exported.\n");
                        break;
                    case 12:
                        if (flags[11]) {
                            System.out.println("Effective Time have already been exported.");
                            break;
                        } else if (!flags[4] || !flags[7]) {
                            System.out.println("Machines and production order must be exported first.\n");
                            break;
                        }
                        theController.exportEffectiveTimes();
                        flags[11] = true;
                        System.out.println("Effective Time exported.\n");
                        break;
                    case 13:
                        if (flags[12]) {
                            System.out.println("Brute Time have already been exported.");
                            break;
                        } else if (!flags[4] || !flags[7]) {
                            System.out.println("Machines and production order must be exported first.\n");
                            break;
                        }
                        theController.exportBruteTime();
                        flags[12] = true;
                        System.out.println("Brute Time exported.\n");
                        break;
                    default:
                        break;
                }
            } while (choice != 0);
        }
        theController.exportEndFile();
        if (theController.validateFile(fileName)) {
            System.out.println("Information has been exported. XML file has no errors and is valid.");
        } else {
            System.out.println("Information has been exported. XML file has at least one error and is invalid.");
        }
        System.out.println("Do you wish to transform the xml file?");
        selector.show();
        answer = selector.selectedElement();
        if (answer == null) {
            return false;
        }
        List<String> formats = new ArrayList<>();
        formats.add(XHTML);
        formats.add(JSON);
        formats.add(TEXT);
        formats.add(XML);
        formats.add(ALL);
        if (answer.equals(YES)) {
            final SelectWidget<String> selector3 = new SelectWidget<>("Formats: ", formats, new OptionsPrinter());
            selector3.show();
            answer = selector3.selectedElement();
            if (answer == null) {
                return false;
            }
            if (answer.equals(ALL)) {
                try {
                    transformationController.tranformXML(fileName, XHTML);
                    transformationController.tranformXML(fileName, JSON);
                    transformationController.tranformXML(fileName, TEXT);
                    transformationController.tranformXML(fileName, XML);
                } catch (TransformerException ex) {
                    System.out.println("File couldn't be transformed");
                } catch (Exception e) {
                    System.out.println("File couldn't be transformed");
                }
                System.out.println("File transformed\n");
            } else {
                try {
                    transformationController.tranformXML(fileName, answer);
                    System.out.println("File transformed\n");
                } catch (TransformerException ex) {
                    System.out.println("File couldn't be transformed");
                } catch (Exception e) {
                    System.out.println("File couldn't be transformed");
                }
            }
        }
        return false;
    }

    private boolean answeredYes(String answer) {
        return answer.equals(YES);
    }

    private void exportAll() {
        theController.exportRawMaterialCategories();
        theController.exportRawMaterials();
        theController.exportProducts();
        theController.exportDeposits();
        theController.exportProductionSheets();
        theController.exportLots();
        theController.exportProductionOrders();
        theController.exportProductionLines();
        theController.exportIntake();
        theController.exportWaste();
        theController.exportMachines();
        theController.exportEffectiveTimes();
        theController.exportBruteTime();
    }

    @Override
    public String headline() {
        return "Export Information";
    }

    private String findFormat(String filename) {
        for (int i = filename.length() - 1; i > 0; i--) {
            if (filename.charAt(i) == '.') {
                return filename.substring(i);
            }
        }
        return null;
    }

}
