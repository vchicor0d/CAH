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
            eligeGanadora();
        }
        if (juego.getGanador().size() == 1) {
            System.out.println("\n¡Gana el jugador "+juego.getGanador().get(0)+"!");
        } else if (juego.getGanador().size() > 1) {
            String mensaje = "\n¡Ha habido un empate entre los jugadores ";
            for (int i=0; i < juego.getGanador().size(); i++){
                if (i != 0){
                    if (i+1 != juego.getGanador().size()){
                        mensaje += ", ";
                    } else {
                        mensaje += " y ";
                    }
                }
                mensaje += juego.getGanador().get(i);
            }
            System.out.println(mensaje+"!");
        } else {
            System.out.println("\nNo ha ganado nadie :(");
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
    
    private void eligeGanadora() {
        System.out.println("\nJugador "+(juego.getZar()+1)+" (Zar), elige una respuesta ganadora:\n");
        System.out.println(pregunta.getTexto());
        System.out.println("______________________________\n");
        for (int i = 0, r = 1; i < juego.getRespuestasTurno().size(); i++, r++) {
            System.out.println("Respuesta: "+r);
            System.out.println("Jugador: "+juego.getRespuestasTurno().get(i).getUserID());
            for (int j = 0; j < pick; j++){
                if (i < juego.getRespuestasTurno().size()) {
                    System.out.println(juego.getRespuestasTurno().get(i).getTexto());
                }
                if (j+1 < pick) {
                    i++;
                }
            }
            System.out.println("............................\n");
        }
        boolean correcto;
        do {
            correcto = true;
            String elegida;
            try {
                elegida = scan.nextLine();
                if (Integer.parseInt(elegida) < 1 || Integer.parseInt(elegida) > jugadores-1){
                    System.err.println("No se ha introducido un número correcto");
                    correcto = false;
                } else {
                    //Se obtiene el ganador del turno sacandolo de la carta de la lista de respuestas mediante la formula pick*respuestaElegida-1
                    juego.ganadorTurno(juego.getRespuestasTurno().get(pick*Integer.parseInt(elegida)-1).getUserID());
                }
            } catch (NumberFormatException e) {
                System.err.println("Se debe introducir un número");
                correcto = false;
            }
        } while (!correcto);
    }
}
