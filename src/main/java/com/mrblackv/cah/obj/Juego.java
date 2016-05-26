/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.obj;

import com.mrblackv.cah.dao.DAOcah;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v.chico
 */
public class Juego {
    
    private List<Jugador> jugadores;
    private int zar;
    private int turnos;
    private int turnoActual;
    private List<Carta> preguntas;
    private List<Carta> respuestas;

    public Juego(int jugadores) {
        this.jugadores = new ArrayList<>();
        for (int i = 0; i<jugadores; i++){
            this.jugadores.add(new Jugador(i));
        }
        preguntas = DAOcah.getCartas(false, "ESP");
        respuestas = DAOcah.getCartas(true, "ESP");
        
        turnos = jugadores*2+1;
        zar = (int) Math.floor(Math.random()*jugadores);
        
        repartir(10);
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public int getZar() {
        return zar;
    }

    public void setZar(int zar) {
        this.zar = zar;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public boolean nuevoTurno() {
        if (++zar == jugadores.size()) zar = 0;
        int cartasEnJuego = 0;
        for (Jugador j : jugadores) {
            cartasEnJuego += j.getMano().size();
        }
        int necesarias = jugadores.size()*10;
        if (necesarias-cartasEnJuego > respuestas.size()) {
            respuestas = DAOcah.getCartas(true, "ESP");
            List<Carta> eliminar = new ArrayList<>();
            for (Jugador j : jugadores) {
                for (Carta c : j.getMano()) {
                    for (Carta r : respuestas) {
                        if (r.getCardId() == c.getCardId()){
                            eliminar.add(r);
                        }
                    }
                }
            }
            respuestas.removeAll(eliminar);
        }
        repartir(null);
        return turnoActual++ != turnos;
    }

    public List<Carta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Carta> preguntas) {
        this.preguntas = preguntas;
    }

    public List<Carta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Carta> respuestas) {
        this.respuestas = respuestas;
    }
    
    public void repartir(Integer numero) {
        int cuantas;
        for (Jugador j : this.jugadores) {
            List<Carta> mano = j.getMano();
            if (mano == null) {
                mano = new ArrayList<>();
            }
            if (numero == null) {
                cuantas = 10-j.getMano().size();
            } else {
                cuantas = numero;
            }
            for (int i = 0 ; i < cuantas; i++) {
                int carta = (int) Math.floor(Math.random()*respuestas.size());
                mano.add(respuestas.get(carta));
                respuestas.remove(carta);
            }
            j.setMano(mano);
        }
    }
    
    public Carta getPregunta() {
        if (preguntas.isEmpty()) preguntas = DAOcah.getCartas(false, "ESP");
        int carta = (int) Math.floor(Math.random()*preguntas.size());
        Carta c = preguntas.get(carta);
        preguntas.remove(carta);
        return c;
    }
}
