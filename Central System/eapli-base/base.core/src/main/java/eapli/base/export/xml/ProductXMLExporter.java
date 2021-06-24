package eapli.base.export.xml;

import eapli.base.productmanagement.domain.Product;

import java.io.PrintWriter;

public class ProductXMLExporter {
    public void export(final Iterable<Product> products, final PrintWriter stream) {
        stream.println("<Products>");
        for(Product p: products) {
            stream.printf("<Product fabricationCode=\"%s\">%n", p.identity());
                stream.printf("<comercialCode>%s</comercialCode>%n",p.comercialCode());
                stream.printf("<briefDescription>%s</briefDescription>%n",p.description());
                stream.printf("<completeDescription>%s</completeDescription>%n",p.completeDescription());
                stream.printf("<productCategory>%s</productCategory>%n",p.productCategory());
                stream.printf("<unity>%s</unity>%n",p.unity());
                stream.printf("<productionSheet>%s</productionSheet>%n",p.productionSheet());
            stream.printf("</Product>%n");
        }
        stream.println("</Products>");
    }
}
