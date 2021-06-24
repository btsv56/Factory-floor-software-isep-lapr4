package eapli.base.export.xml;

import eapli.base.factoryfloormanagementarea.domain.Machine;
import eapli.base.productionlinemanagement.domain.ProductionLine;

import java.io.PrintWriter;

public class ProductionLineXMLExporter {
    public void export(final Iterable<ProductionLine> productionLines, final PrintWriter stream) {
        stream.println("    <ProductionLines>");
        for(ProductionLine pl : productionLines) {
            stream.printf("        <ProductionLine internalCode=\"%s\">%n", pl.identity());
            stream.printf("            <Description>%s</Description>%n", pl.getDesc());
            if (!pl.getMachines().isEmpty()) {
                stream.printf("            <Machines>%n");
                for (Machine m : pl.getMachines()) {
                    stream.printf("                <Machine ID=\"%s\"/>%n", m.identity());
                }
                stream.printf("            </Machines>%n");
            }
            stream.printf("        </ProductionLine>%n");
        }
        stream.println("    </ProductionLines>");
    }
}
