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
