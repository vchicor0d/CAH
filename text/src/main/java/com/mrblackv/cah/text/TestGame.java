/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.text;

import com.mrblackv.cah.obj.Carta;
import com.mrblackv.cah.obj.Juego;
import com.mrblackv.cah.obj.Jugador;
import java.util.Map;

/**
 *
 * @author v.chico
 */
public class TestGame {
    
    public static void start() {
        int jugadores = 4;
        Juego juego = new Juego(jugadores);
        Carta pregunta;
        while (juego.nuevoTurno()) {
            pregunta = juego.getPregunta();
            System.out.println("Turno: "+juego.getTurnoActual()+"\n");
            System.out.println("Jugador: " + (juego.getZar()+1) + " (Zar)");
            System.out.println(pregunta.toString());
            System.out.println(".......................\n");
            for (Jugador j : juego.getJugadores()) {
                if (j.getId() != juego.getZar()) {
                    int sacar = 1;
                    if (pregunta.getExtra() != null) {
                        Map<String, Integer> extra = pregunta.getExtraMap();
                        if (extra.get("DRAW") != null) {
                            juego.repartir(extra.get("DRAW"));
                        }
                        if (extra.get("PICK") != null) {
                            sacar = extra.get("PICK");
                        }
                    }
                    System.out.println("Jugador: " + (j.getId()+1));
                    for (int i = 0; i < sacar; i++){
                        int carta = (int) Math.floor(Math.random()*j.getMano().size());
                        System.out.println(j.getMano().get(carta).toString());
                        j.getMano().remove(carta);
                    }
                    System.out.println(".......................\n");
                }
            }
            System.out.println("_________________________");
            System.out.println("_________________________\n");
        }
    }
    
}
