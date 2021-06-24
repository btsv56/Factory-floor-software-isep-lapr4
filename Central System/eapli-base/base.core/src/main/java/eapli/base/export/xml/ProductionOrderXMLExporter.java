package eapli.base.export.xml;

import eapli.base.productionordermanagement.domain.ProductionOrder;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class ProductionOrderXMLExporter {
    public void export(final Iterable<ProductionOrder> productionOrders, final PrintWriter stream) {
        if (!productionOrders.iterator().hasNext()) return;
        stream.println("    <ProductionOrders>");
        for(ProductionOrder po: productionOrders) {
            stream.printf("        <ProductionOrder internalCode=\"%s\">%n",po.getInternalCode());
            stream.printf("            <Description>%s</Description>%n",po.getDesc());
            stream.printf("            <State>%s</State>%n",po.getState().getState());
            stream.printf("            <Lot ID=\"%s\"/>%n",po.getLot());
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
            stream.printf("            <EmissionDate>%s</EmissionDate>%n",sdf.format(po.getEmissionDate().getDate().getTime()));
            stream.printf("            <PredictedExecutionDate>%s</PredictedExecutionDate>%n",sdf.format(po.getPredictedExecutionDate().getDate().getTime()));
            stream.printf("            <ProductionSheet ID=\"%s\"/>%n",po.getProductionSheet().identity());
            stream.printf("            <QuantityToProduce>%d</QuantityToProduce>%n",po.getQuantityToProduce());
            stream.printf("        </ProductionOrder>%n");
        }
        stream.println("    </ProductionOrders>");
    }
}
