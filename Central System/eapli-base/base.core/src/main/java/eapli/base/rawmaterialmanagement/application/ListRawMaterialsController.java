/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eapli.base.rawmaterialmanagement.application;

import eapli.base.rawmaterialmanagement.domain.RawMaterial;

/**
 *
 * @author Utilizador
 */
public class ListRawMaterialsController {

    final ListRawMaterialService svc= new ListRawMaterialService();

    /**
     * All raw materials
     * @return all raw materials
     */
    public Iterable<RawMaterial> allRawMaterials(){
        return this.svc.allRawMaterials();
    }

}

