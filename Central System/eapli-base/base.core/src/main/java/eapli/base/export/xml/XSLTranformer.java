/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export.xml;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 *
 * @author Utilizador
 */
public class XSLTranformer {

    public void transformXML(String source, String type) throws TransformerConfigurationException, TransformerException {
        for (int i = 1; i < 4; i++) {
            if (!((type.equals("Text") && i > 2) || (type.equals("XML") && i >1 ))) {
                String file = String.format(".\\xml\\XMLto%s\\XMLto%s%d.xsl", type, type, i);
                String output = String.format(".\\xml\\output%d.%s",i, type);
                System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
                TransformerFactory factory = TransformerFactory.newInstance();
                Source xslt = new StreamSource(new File(file));
                Transformer transformer = factory.newTransformer(xslt);
                Source text = new StreamSource(new File(".\\xml\\"+source));
                transformer.transform(text, new StreamResult(new File(output)));
            }
        }
    }

}
