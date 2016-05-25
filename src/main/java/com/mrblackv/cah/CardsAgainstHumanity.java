/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah;

import com.mrblackv.cah.dao.DAOcah;
import com.mrblackv.cah.obj.Carta;
import java.util.List;

/**
 *
 * @author v.chico
 */
public class CardsAgainstHumanity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int jugadores;
        List<Carta> mazo = DAOcah.getCartas(true, "ESP");
        if (mazo != null && !mazo.isEmpty()) {
            mazo.stream().forEach((c) -> {
                System.out.println(c.toString()+"\n");
            });
        }
    }
    
}
