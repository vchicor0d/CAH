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
import java.util.Scanner;

/**
 *
 * @author v.chico
 */
public class JuegoTexto {
    
    private int jugadores;
    private Juego juego;
    private Carta pregunta;
    private Scanner scan;
    private int pick;
    
    public JuegoTexto() {
        scan = new Scanner(System.in);
        System.out.println("Cartas contra los Homínidos");
        System.out.println("___________________________\n");
        configuraJuego();
        juego();
    }
    
    private void configuraJuego() {
        boolean correcto;
        do {
            correcto = true;
            System.out.println("¿Cuántos jugadores? (Mínimo 3)");
            String jug;
            try {
                jug = scan.nextLine();
                jugadores = Integer.parseInt(jug);
                if (jugadores < 3) {
                    correcto = false;
                    System.err.println("Número de jugadores incorrecto");
                }
            } catch (NumberFormatException e) {
                System.err.println("Se debe introducir un número");
                correcto = false;
            }
        } while (!correcto);
        juego = new Juego(jugadores);
        String turnos = null;
        do {
            correcto = true;
            System.out.println("¿Cuantos turnos quiere jugar? (Mínimo 1, por defecto "+juego.getTurnos()+")");
            try { 
                turnos = scan.nextLine();
                if (Integer.parseInt(turnos) < 1) {
                    System.err.println("Número de turnos incorrecto");
                    correcto = false;
                } else {
                    juego.setTurnos(Integer.parseInt(turnos));
                }
            } catch (NumberFormatException e) {
                if ("".equals(turnos)) {
                    System.out.println("Se jugarán "+juego.getTurnos()+" turnos");
                } else {
                    System.err.println("Se debe introducir un número");
                    correcto = false;
                }
            }
        } while (!correcto);
        String zar = null;
        do {
            correcto = true;
            System.out.println("¿Quién será el Zar en el primer turno (Introduzca un número del 1 al "+jugadores+")? (Por defecto aleatorio)");
            try { 
                zar = scan.nextLine();
                if (Integer.parseInt(zar) < 1 || Integer.parseInt(zar) > jugadores) {
                    System.err.println("Número de jugador incorrecto");
                    correcto = false;
                } else {
                    juego.setZar(Integer.parseInt(zar));
                }
            } catch (NumberFormatException e) {
                if (!"".equals(zar)) {
                    System.err.println("Se debe introducir un número");
                    correcto = false;
                }
            }
        } while (!correcto);
    }
    
    private void juego() {
        while (juego.nuevoTurno()) {
            pregunta = juego.getPreguntaTurno();
            System.out.println("Turno: "+juego.getTurnoActual()+"\n");
            System.out.println("Jugador: " + (juego.getZar()+1) + " (Zar)");
            System.out.println(".......................\n");
            pick = 1;
            if (pregunta.getExtra() != null) {
                Map<String, Integer> extra = pregunta.getExtraMap();
                if (extra.get("DRAW") != null) {
                    juego.repartir(extra.get("DRAW"));
                }
                if (extra.get("PICK") != null) {
                    pick = extra.get("PICK");
                }
            }
            for (Jugador j : juego.getJugadores()) {
                if (j.getId() != juego.getZar()) {
                    getRespuestas(j);
                }
            }
            System.out.println(pregunta.getTexto());
            System.out.println("______________________________\n");
            for (Carta c : juego.getRespuestasTurno()) {
                System.out.println("Jugador: "+c.getUserID());
                System.out.println(c.getTexto()+"\n");
            }
        }
    }
    
    private void getRespuestas(Jugador j) {
        int elegidas = 0;
        boolean correcto;
        do {
            System.out.println("Jugador: " + (j.getId()+1));
            System.out.println("Elige una carta: \n");
            System.out.println(pregunta.getTexto());
            System.out.println(".......................\n");
            for (int i = 0; i<j.getMano().size(); i++) {
                System.out.println("Carta "+(i+1)+":");
                System.out.println(j.getMano().get(i).getTexto()+"\n");
            }
            do {
                correcto = true;
                String elegida;
                try {
                    elegida = scan.nextLine();
                    if (Integer.parseInt(elegida) < 1 || Integer.parseInt(elegida) > j.getMano().size()){
                        System.err.println("No se ha introducido un número correcto");
                        correcto = false;
                    } else {
                        Carta c = j.getMano().get(Integer.parseInt(elegida)-1);
                        c.setUserID(j.getId()+1);
                        j.getMano().remove(Integer.parseInt(elegida)-1);
                        juego.addRespuesta(c);
                        elegidas++;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Se debe introducir un número");
                    correcto = false;
                }
            } while (!correcto);
        } while (elegidas < pick);
    }
}
