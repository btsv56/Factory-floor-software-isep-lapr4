/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export.xml;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;
import java.io.PrintWriter;

/**
 *
 * @author Utilizador
 */
public class RawMaterialXMLExporter {
    public void export(final Iterable<RawMaterial> rawMaterials, final PrintWriter stream) {
        stream.println("<RawMaterials>");
        for(RawMaterial rm: rawMaterials) {
            stream.printf("<RawMaterial internalCode=\"%s\" nameRawMaterialCategory=\"%s\">%n",rm.internalCode(),rm.category());
            stream.printf("<description>%s</description>%n",rm.description());
            stream.println("<TechnicalSheet>");
            stream.printf("<nameTechnicalSheet>%s</nameTechnicalSheet>%n", rm.technicalSheet());
            stream.println("</TechnicalSheet>");
            stream.printf("</RawMaterial>%n");
        }
        stream.println("</RawMaterials>");
    }
} 
