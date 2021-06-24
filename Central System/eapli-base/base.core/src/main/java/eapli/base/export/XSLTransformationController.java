/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.export;

import eapli.base.export.xml.XSLTranformer;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Utilizador
 */
public class XSLTransformationController {
    
    public void tranformXML(String source,String type) throws TransformerException {
        new XSLTranformer().transformXML(source,type);
    }
}
