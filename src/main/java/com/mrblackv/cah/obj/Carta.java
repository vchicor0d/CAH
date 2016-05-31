/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.obj;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author v.chico
 */
@XmlRootElement(name = "carta")
@XmlType(propOrder = {"cardId", "idioma", "tipo", "texto", "extra"})
public class Carta implements Serializable {
    private long cardId;
    private String texto;
    private boolean tipo;
    private String idioma;
    private String userID;
    private String extra;

    public Carta() {
    }

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

    @XmlTransient
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @XmlElement(name = "id")
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

    public Map<String, Integer> getExtraMap() {
        String[] params = null;
        if (extra != null) {
            params = extra.split(",");
        }
        Map<String, Integer> paramMap = null;
        if (params != null && params.length>0) {
            paramMap = new HashMap<>();
            for (String param : params) {
                String [] paramSplit = param.split("=");
                if (paramSplit!= null && paramSplit.length > 0) {
                    paramMap.put(paramSplit[0], Integer.parseInt(paramSplit[1]));
                }
            }
        }
        return paramMap;
    }
    
    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.cardId ^ (this.cardId >>> 32));
        hash = 17 * hash + Objects.hashCode(this.texto);
        hash = 17 * hash + (this.tipo ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.idioma);
        hash = 17 * hash + Objects.hashCode(this.userID);
        hash = 17 * hash + Objects.hashCode(this.extra);
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
        if (this.cardId != other.cardId) {
            return false;
        }
        if (this.tipo != other.tipo) {
            return false;
        }
        if (!Objects.equals(this.texto, other.texto)) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        if (!Objects.equals(this.extra, other.extra)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        String toString = cardId+": "+((tipo)?"Respuesta ":"Pregunta ")+idioma+"\n"+texto;
        Map<String, Integer> extramap = getExtraMap();
        if (extramap != null && !extramap.isEmpty()){
            Set<String> claves = extramap.keySet();
            for (String clave : claves) {
                toString += "\n"+clave+": "+extramap.get(clave);
            }
        }
        return toString;
    }
}
