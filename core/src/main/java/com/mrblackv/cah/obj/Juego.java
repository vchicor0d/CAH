/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.obj;

import com.mrblackv.cah.dao.DAOcah;
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
    private Carta preguntaTurno;
    private List<Carta> respuestasTurno;
    private DAOcah dao;

    public Juego(int jugadores) {
        dao = new DAOcah();
        this.jugadores = new ArrayList<>();
        for (int i = 0; i<jugadores; i++){
            this.jugadores.add(new Jugador(i));
        }
        preguntas = dao.getCartas(false, "ESP");
        respuestas = dao.getCartas(true, "ESP");
        
        turnos = jugadores*2+1;
        zar = (int) Math.floor(Math.random()*jugadores);
        
        repartir(10);
    }

    public boolean nuevoTurno() {
        if (++zar == jugadores.size()) zar = 0;
        int cartasEnJuego = 0;
        for (Jugador j : jugadores) {
            cartasEnJuego += j.getMano().size();
        }
        int necesarias = jugadores.size()*10;
        if (necesarias-cartasEnJuego > respuestas.size()) {
            rellenaRespuestas();
        }
        repartir(null);
        preguntaTurno = getPregunta();
        respuestasTurno = new ArrayList<>();
        return turnoActual++ != turnos;
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
            if (respuestas.size()<cuantas){
                rellenaRespuestas();
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
        if (preguntas.isEmpty()) preguntas = dao.getCartas(false, "ESP");
        int carta = (int) Math.floor(Math.random()*preguntas.size());
        Carta c = preguntas.get(carta);
        preguntas.remove(carta);
        return c;
    }
    
    private void rellenaRespuestas() {
        respuestas = dao.getCartas(true, "ESP");
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

    public Carta getPreguntaTurno() {
        return preguntaTurno;
    }

    public void setPreguntaTurno(Carta preguntaTurno) {
        this.preguntaTurno = preguntaTurno;
    }

    public List<Carta> getRespuestasTurno() {
        return respuestasTurno;
    }

    public void setRespuestasTurno(List<Carta> respuestasTurno) {
        this.respuestasTurno = respuestasTurno;
    }
    
    public void addRespuesta(Carta c) {
        respuestasTurno.add(c);
    }
}
