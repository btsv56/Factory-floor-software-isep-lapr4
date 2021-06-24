package eapli.base.export.xml;

import eapli.base.productionordermanagement.domain.Lot;

import java.io.PrintWriter;

public class LotXMLExporter {
    public void export(final Iterable<Lot> lots, final PrintWriter stream) {
        if (!lots.iterator().hasNext()) return;
        stream.println("    <Lots>");
        for(Lot l : lots) {
            stream.printf("        <Lot internalCode=\"%s\"/>%n", l.toString());
        }
        stream.println("    </Lots>");
    }
}
