package eapli.base.productmanagement.imports;

import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.domain.ProductBuilder;
import eapli.framework.domain.model.general.Designation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CSVProductImporter implements ProductImporter {

    @Override
    public List<Product> begin(String filename) throws IOException {
        List<Product> impProducts = new LinkedList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filename));

            br.readLine();
            int currentLine = 1 ;
            String line = br.readLine();
            while (line != null){
                String[] values = line.split(";");
                if(values.length != 6){
                    throw new Exception(String.format("Error parsing the file at line %d", currentLine));
                }

                final ProductBuilder productbuilder = new ProductBuilder();
                productbuilder.withoutProductionSheet(Designation.valueOf(values[0]),
                        values[1], values[2], values[3], values[4], values[5]);
                impProducts.add(productbuilder.build());
                line = br.readLine();
                currentLine++;
            }

            br.close();

        } catch (FileNotFoundException fex){
            System.err.println(String.format("File %s not found!\n",filename));
        } catch(IOException ioe) {
            System.err.println(String.format("Error opening File!\n", filename));
        } catch(Exception e) {
            System.err.println(String.format("%s\n", e.getMessage()));
        } finally {
            if (br != null)
                br.close();
        }

        return impProducts;
    }
}
