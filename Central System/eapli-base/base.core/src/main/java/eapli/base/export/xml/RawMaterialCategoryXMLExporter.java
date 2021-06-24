/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export.xml;


import eapli.base.rawmaterialmanagement.domain.RawMaterialCategory;
import java.io.PrintWriter;

/**
 *
 * @author Utilizador
 */
public class RawMaterialCategoryXMLExporter {
    public void export(final Iterable<RawMaterialCategory> rawMaterials, final PrintWriter stream) {
        stream.println("<RawMaterialCategories>");
        for(RawMaterialCategory rmc: rawMaterials) {
            stream.printf("<RawMaterialCategory name=\"%s\">%n",rmc.identity());
            stream.printf("<description>%s</description>%n",rmc.description());
            stream.printf("</RawMaterialCategory>%n");
        }
        stream.println("</RawMaterialCategories>");
    }
}
