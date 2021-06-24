# [#34: [5-1-2007] Export to a XML file all information](https://bitbucket.org/pjoliveira/lei_isep_2019_20_sem4_2db_1180573_1180715_1180723_1180712/issues/34/5-1-2007-export-to-a-xml-file-all)

# 1. Requirements 

As SCM 
I want to currently import existing messages in text files that are present in the input directory
So that they are available for processing

Acceptance criteria:

 - Java application using threads
 - After being imported the files should be moved to a new directory of processed files
 - Both directories should be defined by configuration

# 2. Design

This design is identical to a regular XML exporter use case.

## 2.2. Testes 
*Nesta secção deve sistematizar como os testes foram concebidos para permitir uma correta aferição da satisfação dos requisitos.*

**Scenario 1:** 

- After starting the backoffice application, choose the option export information to xml and select the option export all and enter the path
- The application will then proceed to export messages to the file, and finally validate the file with the XSD created

# 3. Implementation

### **3.1 Machine**

```java
package eapli.base.export.xml;

import eapli.base.factoryfloormanagementarea.domain.ConfigurationFile;
import eapli.base.factoryfloormanagementarea.domain.Machine;

import java.io.PrintWriter;

public class MachineXMLExporter {
    public void export(final Iterable<Machine> machines, final PrintWriter stream) {
        stream.println("<Machines>");
        for(Machine m: machines) {
            stream.printf("<Machine InternalCode=\"%s\" State=\"%s\">%n", m.identity(), m.state());
            stream.printf("<SerialNumber>%s</SerialNumber>%n",m.serialNumber());
            stream.printf("<Description>%s</Description>%n",m.description());
            stream.printf("<InstallationDate>%s</InstallationDate>%n",m.installationDate());
            stream.printf("<Brand>%s</Brand>%n",m.brand());
            stream.printf("<Model>%s</Model>%n",m.model());
            stream.printf("<Protocol ID=\"%s\"/>%n",m.protocol().getInt());
            stream.printf("<Machine InternalCode=\"%s\"/>%n",m.followedByMachine().identity());
            stream.printf("<ConfigurationFiles>%n");
            for(ConfigurationFile cf : m.configList()){
                stream.printf("<ConfigurationFile File=\"%s\">%s</ConfigurationFile>%n", cf.toString(),cf.getDescription());
            }
            stream.printf("</ConfigurationFiles>%n");
            stream.printf("</Machine>%n");
        }
        stream.println("</Machines>");
    }
}

```

### **3.2 Brute Time**

```java
package eapli.base.export.xml;

import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.domain.ProductionTime;

import java.io.PrintWriter;

public class BruteTimeXMLExporter {

    public void export(final Iterable<ProductionResult> productionResults, final PrintWriter stream) {
        stream.println("<BruteTimes>");
        for (ProductionResult pr : productionResults) {
            for (ProductionTime pt : pr.getBruteTimes()) {
                stream.printf("<BruteTime Minutes=\"%s\" Seconds=\"%s\">%n", pt.minutes(), pt.seconds());
                stream.printf("<Machine InternalCode=\"%s\"></Machine>", pt.machine().internalCode());
                stream.printf("<ProductionOrder ID=\"po1\"></ProductionOrder>%n", pr.order());
                stream.printf("</BruteTime>%n");
            }
        }
        stream.println("</BruteTimes>");
    }
}


```

### **3.3 Effective Time**

```java
package eapli.base.export.xml;

import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.domain.ProductionTime;

import java.io.PrintWriter;

public class EffectiveTimeXMLExporter {

    public void export(final Iterable<ProductionResult> productionResults, final PrintWriter stream) {
        stream.println("<EffectiveTimes>");
        for (ProductionResult pr : productionResults) {
            for (ProductionTime pt : pr.getEffectiveTimes()) {
                stream.printf("<EffectiveTime Minutes=\"%s\" Seconds=\"%s\">%n", pt.minutes(), pt.seconds());
                stream.printf("<Machine InternalCode=\"%s\"></Machine>", pt.machine().internalCode());
                stream.printf("<ProductionOrder ID=\"po1\"></ProductionOrder>%n", pr.order());
                stream.printf("</EffectiveTime>%n");
            }
        }
        stream.println("</EffectiveTimes>");
    }
}
```

### **3.4 Wastes**

```java
package eapli.base.export.xml;

import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.domain.StockMovement;

import java.io.PrintWriter;

public class WastesXMLExporter {

    public void export(final Iterable<ProductionResult> productionResults, final PrintWriter stream) {
        stream.println("<Wastes>");
        for (ProductionResult pr : productionResults) {
            for (StockMovement sm : pr.getWastes()) {
                stream.printf("<Waste quantity=\"%s\">%n", sm.getQuantaty());
                stream.printf("<Machine InternalCode=\"%s\"/>", sm.machine());
                stream.printf("<Deposit ID=\"%s\"/>%n", sm.deposit().getCode());
                if (sm.rawMaterial() != null) {
                    stream.printf("<RawMaterial ID=\"%s\"/>%n", sm.rawMaterial().internalCode());
                }
                if (sm.product() != null) {
                    stream.printf("<Product ID=\"%s\"/>%n", sm.product().comercialCode());
                }
                stream.printf("<ProductionOrder ID=\"po1\"/>%n", pr.order().getInternalCode());
                stream.printf("</Waste>%n");
            }
        }
        stream.println("</Wastes>");
    }
}
```



# 4. Integration/Demonstration

### **4.1 InformationExporterXML**

```java
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
```

### **4.2 ExporterInformationUI**

```java
public class ExportInformationtUI extends AbstractUI {

    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String RETURN = "Return";

    private final ExportInformationController theController = new ExportInformationController();

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
            boolean[] flags = new boolean[10];
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
                        } else if (!flags[1] || !flags[2] || flags[3] || flags[5]) {
                            System.out.println("Raw materials, products, machines and deposits must be exported first.\n");
                        }
                        theController.exportIntake();
                        break;
                    case 11:
                        if (flags[10]) {
                            System.out.println("Wastes have already been exported.");
                            break;
                        } else if (!flags[1] || !flags[2] || !flags[4] || !flags[5] || !flags[7]) {
                            System.out.println("Raw materials, products, machines, deposits and production order must be exported first.\n");
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
            System.out.println("Valid file");
        } else {
            System.out.println("Invalid file");
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
        theController.exportMachines();
        theController.exportProductionSheets();
        theController.exportProductionOrders();
        theController.exportLots();
        theController.exportProductionLines();
        theController.exportIntake();
        theController.exportBruteTime();
        theController.exportWaste();
        theController.exportEffectiveTimes();
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
```



# 5. Observations

None.



