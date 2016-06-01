/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.text;

import com.mrblackv.cah.obj.Carta;
import com.mrblackv.cah.obj.Juego;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
    
    public JuegoTexto() {
        scan = new Scanner(System.in);
        System.out.println("Cartas contra los Homínidos");
        System.out.println("___________________________\n");
        configuraJuego();
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
    
}
