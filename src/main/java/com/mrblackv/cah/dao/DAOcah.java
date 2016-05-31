/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.dao;

import com.mrblackv.cah.obj.Carta;
import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author v.chico
 */
public class DAOcah {

    public DAOcah() {}
    
    private Connection getConnection(){
        Connection c = null;
        try {
            //System.out.println("Creando conexion: ");
            Class.forName("org.sqlite.JDBC");
            String path = DAOcah.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+"/../db/cah";
            //System.out.println(path);
            c = DriverManager.getConnection("jdbc:sqlite:"+path);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("getConnection: "+e);
            System.exit(1);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DAOcah.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    
    public List<Carta> getCartas(boolean tipo, String idioma){
        Connection c = getConnection();
        List<Carta> mazo = null;
        if (c!=null){
            String query = "select cardId, texto, tipo, idioma, extra from cartas where tipo = ? and idioma = ?";
            try {
                PreparedStatement pst = c.prepareStatement(query);
                pst.setBoolean(1, tipo);
                pst.setString(2, idioma);
                ResultSet rs = pst.executeQuery();
                mazo = new ArrayList<>();
                Carta card;
                while (rs.next()) {
                    card = new Carta(rs.getLong(1), rs.getString(2), rs.getBoolean(3), rs.getString(4));
                    card.setExtra(rs.getString(5));
                    mazo.add(card);
                }
            } catch (SQLException sqle) {
                System.err.println("getCartas: "+sqle);
                System.exit(2);
            }
        }
        return mazo;
    }
    
    public List<Carta> getCartas() {
        try {
            JAXBContext contexto = JAXBContext.newInstance(Carta.class);
            Unmarshaller un = contexto.createUnmarshaller();
            String path = DAOcah.class.getClassLoader().getResource("db/cartas.xml").getPath();
            File xml = new File(path);
            Carta c = (Carta) un.unmarshal(xml);
            System.out.println(c.toString());
        } catch (JAXBException e) {
            System.err.println("getCartas: "+e);
            System.exit(2);
        }
        return null;
    }
    
}
