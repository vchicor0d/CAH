/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mrblackv.cah.obj.Carta;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v.chico
 */
public class DAOcah {

    public DAOcah() {}
    
    public List<Carta> getCartas(boolean tipo, String idioma){
        List<Carta> mazo = getCartas();
        List<Carta> lista = new ArrayList<>();
        if (mazo != null && !mazo.isEmpty()) {
            for (Carta c : mazo) {
                if (c.isTipo()==tipo && c.getIdioma().equals(idioma)){
                    lista.add(c);
                }
            }
        }
        return lista;
    }
    
    public List<Carta> getCartas() {
        Gson gson = new Gson();
        List<Carta> lista = null;
        try {
            InputStreamReader isr = new InputStreamReader(DAOcah.class.getClassLoader().getResourceAsStream("db/cartas.json"),"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            Reader reader = br;
            lista = gson.fromJson(reader, new TypeToken<List<Carta>>(){}.getType());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DAOcah.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
