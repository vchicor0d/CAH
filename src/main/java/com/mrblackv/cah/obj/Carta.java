/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.obj;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author v.chico
 */
public class Carta implements Serializable {
    private long cardId;
    private String texto;
    private boolean tipo;
    private String idioma;
    private String userID;

    public Carta(long cardId, String texto, boolean tipo, String idioma) {
        this.cardId = cardId;
        this.texto = texto;
        this.tipo = tipo;
        this.idioma = idioma;
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.texto);
        hash = 59 * hash + (this.tipo ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.userID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Carta other = (Carta) obj;
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return cardId+": "+((tipo)?"Respuesta ":"Pregunta ")+idioma+"\n"+texto;
    }
}
