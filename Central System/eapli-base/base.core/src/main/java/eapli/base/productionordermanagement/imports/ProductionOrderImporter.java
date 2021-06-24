package eapli.base.productionordermanagement.imports;

import java.io.IOException;
import java.util.List;

import eapli.base.productionordermanagement.domain.ProductionOrder;

public interface ProductionOrderImporter {

    List<ProductionOrder> begin(String filename) throws IOException;

}
