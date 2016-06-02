/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.text;

/**
 *
 * @author v.chico
 */
public class CardsAgainstHominids {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            TestGame.start();
            new JuegoTexto();
        } else {
            System.out.println("En construccion");
            new JuegoTexto();
        }
    }
    
}
