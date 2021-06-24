package eapli.base.export.xml;

import eapli.base.productionsheetmanagement.domain.ProductionSheet;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineProduct;
import eapli.base.productionsheetmanagement.domain.ProductionSheetLineRawMaterial;

import java.io.PrintWriter;

public class ProductionSheetXMLExporter {
    public void export(final Iterable<ProductionSheet> productionSheets, final PrintWriter stream) {
        stream.println("<ProductionSheets>");
        for(ProductionSheet ps: productionSheets) {
            stream.printf("<ProductionSheet ID=\"%s\">%n",ps.identity());
                for(ProductionSheetLineProduct psp : ps.getBOMItems()) {
                    stream.printf("<ProductionSheetLineProduct>%n");
                        stream.printf("<Product ID=\"%s\" Quantity=\"%s\"/>%n", psp.getProduct().identity(), psp.getQuantity());
                    stream.printf("</ProductionSheetLineProduct>%n");
                }
                for(ProductionSheetLineRawMaterial psr : ps.getBOMMaterials()) {
                    stream.printf("<ProductionSheetLineRawMaterial>%n");
                        stream.printf("<RawMaterial ID=\"%s\" Quantity=\"%s\"/>%n", psr.getMaterial().identity(), psr.getQuantity());
                    stream.printf("</ProductionSheetLineRawMaterial>%n");
                }
            stream.printf("</ProductionSheet>%n");
        }
        stream.println("</ProductionSheets>");
    }
}
