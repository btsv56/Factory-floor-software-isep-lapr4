/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.utils;

import eapli.framework.util.Utility;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bruno
 */
@Utility
public class Converter {

    private Converter() {
    }
    
    public static <T> Set<T> iterableToSet(Iterable<T> iterable){
        Set<T> set=new HashSet<>();
        iterable.forEach(set::add);
        return set;
    }
}
