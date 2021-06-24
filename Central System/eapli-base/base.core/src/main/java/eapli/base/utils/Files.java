/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.utils;

import eapli.framework.util.Utility;
import java.io.IOException;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import java.io.File;

/**
 *
 * @author bruno
 */
@Utility
public class Files{
    
    private Files(){
        
    }
    
    public static byte[] toByteArray(File file) throws IOException{
        return IOUtils.toByteArray(new FileInputStream(file));
    }
}
