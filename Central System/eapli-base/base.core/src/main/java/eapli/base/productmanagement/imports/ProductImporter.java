package eapli.base.productmanagement.imports;

import eapli.base.productmanagement.domain.Product;

import java.io.IOException;
import java.util.List;

public interface
ProductImporter {

    List<Product> begin(String filename) throws IOException;

}
