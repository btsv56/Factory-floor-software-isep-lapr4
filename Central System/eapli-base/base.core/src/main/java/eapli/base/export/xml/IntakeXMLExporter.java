/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export.xml;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.productionresultmanagement.domain.ProductionResult;
import eapli.base.productionresultmanagement.domain.StockMovement;
import eapli.base.productmanagement.domain.Product;
import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import eapli.framework.domain.model.general.Designation;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Utilizador
 */
public class IntakeXMLExporter {

    public void export(final Iterable<ProductionResult> productionResults, final PrintWriter stream) {
        List<StockMovement> stockMovements = new ArrayList<>();
        Map<Designation, Integer> totalConsumptionsDeposit = new HashMap<>();
        Map<Designation, Integer> totalConsumptionsRawMaterial = new HashMap<>();
        Map<Designation, Integer> totalConsumptionsProduct = new HashMap<>();
        stream.println("<Consumptions>");
        stream.println("<EffectiveConsumptions>");
        Deposit deposit;
        Product product;
        RawMaterial rawMaterial;
        for (ProductionResult pr : productionResults) {
            for (StockMovement sm : pr.consumptions()) {
                deposit = sm.deposit();
                rawMaterial = sm.rawMaterial();
                product = sm.product();
                stockMovements.add(sm);
                stream.printf("<Consumption machine=\"%s\" ", sm.machine().identity());
                if (deposit != null) {
                    stream.printf("deposit=\"%s\" ", deposit.identity());
                }
                if (product != null) {
                    stream.printf("product=\"%s\">%n", product.identity());
                }
                if (rawMaterial != null) {
                    stream.printf("rawMaterial=\"%s\">%n", rawMaterial.identity());
                }
                stream.printf("<quantity>%d</quantity>%n", sm.getQuantaty());
                stream.printf("</Consumption>%n");
            }
        }
        stream.println("</EffectiveConsumptions>");
        getTotalConsumptions(stockMovements, totalConsumptionsDeposit, totalConsumptionsRawMaterial, totalConsumptionsProduct);
        stream.println("<RealConsumptions>");
        for (Designation d : totalConsumptionsDeposit.keySet()) {
            stream.printf("<Deposit id=\"%s\">%n", d);
            stream.printf("<quantity>%d</quantity>%n", totalConsumptionsDeposit.get(d));
            stream.printf("</Deposit>%n");
        }
        for (Designation d : totalConsumptionsRawMaterial.keySet()) {
            stream.printf("<RawMaterial id=\"%s\">%n", d);
            stream.printf("<quantity>%d</quantity>%n",totalConsumptionsRawMaterial.get(d));
            stream.printf("</RawMaterial>%n");
        }
        for(Designation d: totalConsumptionsProduct.keySet()) {
            stream.printf("<Product id=\"%s\">%n", d);
            stream.printf("<quantity>%d</quantity>", totalConsumptionsProduct.get(d));
            stream.printf("</Product>");
        }
        stream.println("</RealConsumptions>");
        stream.println("</Consumptions>");
    }

    private void getTotalConsumptions(List<StockMovement> stockMovement, Map<Designation, Integer> totalConsumptionsDeposit,
             Map<Designation, Integer> totalConsumptionsRawMaterial, Map<Designation, Integer> totalConsumptionsProduct) {
        Deposit deposit;
        Product product;
        RawMaterial rawMaterial;
        for (StockMovement sm : stockMovement) {
            deposit = sm.deposit();
            if (deposit != null) {
                if (totalConsumptionsDeposit.containsKey(deposit.identity())) {
                    totalConsumptionsDeposit.put(deposit.identity(), totalConsumptionsDeposit.get(deposit.identity()) + sm.getQuantaty());
                } else {
                    totalConsumptionsDeposit.put(deposit.identity(), sm.getQuantaty());
                }
            }
            product = sm.product();
            if (product != null) {
                if (totalConsumptionsProduct.containsKey(product.identity())) {
                    totalConsumptionsProduct.put(product.identity(), totalConsumptionsProduct.get(product.identity()) + sm.getQuantaty());
                } else {
                    totalConsumptionsProduct.put(product.identity(), sm.getQuantaty());
                }
            }
            rawMaterial = sm.rawMaterial();
            if (rawMaterial != null) {
                if (totalConsumptionsRawMaterial.containsKey(rawMaterial.identity())) {
                    totalConsumptionsRawMaterial.put(rawMaterial.identity(), totalConsumptionsRawMaterial.get(rawMaterial.identity()) + sm.getQuantaty());
                } else {
                    totalConsumptionsRawMaterial.put(rawMaterial.identity(), sm.getQuantaty());
                }
            }
        }

    }
}
