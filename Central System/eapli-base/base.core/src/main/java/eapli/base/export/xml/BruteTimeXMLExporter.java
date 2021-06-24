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
