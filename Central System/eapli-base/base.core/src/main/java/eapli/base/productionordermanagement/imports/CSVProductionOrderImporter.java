package eapli.base.productionordermanagement.imports;

import eapli.base.productionordermanagement.domain.ProductionOrder;
import eapli.base.productionordermanagement.domain.ProductionOrderBuilder;
import eapli.framework.domain.model.general.Designation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CSVProductionOrderImporter implements ProductionOrderImporter {

    @Override
    public List<ProductionOrder> begin(String filename) throws IOException {
        List<ProductionOrder> impProducts = new LinkedList<>();
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filename));

            br.readLine();
            int currentLine = 1 ;
            String line = br.readLine();
            while (line != null){
                String[] values = line.split(";");
                if(values.length != 9){
                    System.out.println("Error parsing the file at line "+ currentLine);
                    line = br.readLine();
                    currentLine++;
                    continue;
                }

                final ProductionOrderBuilder productionOrderBuilder = new ProductionOrderBuilder();
                productionOrderBuilder.gatherVariablesWithoutSheet(Designation.valueOf(values[0]), values[1], values[2], Designation.valueOf(values[3]),
                                                        Designation.valueOf(values[4]), values[5], values[6], Integer.parseInt(values[7]), Designation.valueOf(values[8]));
                impProducts.add(productionOrderBuilder.build());
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
