package eapli.base.export.xml;

import eapli.base.depositmanagement.domain.Deposit;
import eapli.base.depositmanagement.domain.DepositMaterialSheet;
import eapli.base.depositmanagement.domain.DepositProductSheet;

import java.io.PrintWriter;

public class DepositXMLExporter {
    public void export(final Iterable<Deposit> deposits, final PrintWriter stream) {
        stream.println("    <Deposits>");
        for(Deposit d: deposits) {
            stream.printf("        <Deposit internalCode=\"%s\">%n",d.getCode());
            stream.printf("            <Description>%s</Description>%n",d.getDescription());
            if (!d.getMaterialSheets().isEmpty()) {
                stream.printf("            <RawMaterials>%n");
                for (DepositMaterialSheet dms : d.getMaterialSheets()) {
                    stream.printf("                <RawMaterial ID=\"%s\" Quantity=\"%d\"/>%n", dms.getMaterial().internalCode(), dms.getAmount());
                }
                stream.printf("            </RawMaterials>%n");
            }
            if (!d.getProductSheets().isEmpty()) {
                stream.printf("            <Products>%n");
                for (DepositProductSheet dms : d.getProductSheets()) {
                    stream.printf("                <Product ID=\"%s\" Quantity=\"%d\"/>%n", dms.getProduct().identity(), dms.getAmount());
                }
                stream.printf("            </Products>%n");
            }
            stream.printf("        </Deposit>%n");
        }
        stream.println("    </Deposits>");
    }
}
