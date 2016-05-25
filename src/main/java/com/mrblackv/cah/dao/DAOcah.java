/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrblackv.cah.dao;

import com.mrblackv.cah.obj.Carta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author v.chico
 */
public class DAOcah {
    
    private static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cah");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("getConnection: "+e);
            System.exit(1);
        }
        return c;
    }
    
    public static List<Carta> getCartas(boolean tipo, String idioma){
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
                    mazo.add(new Carta(rs.getLong(1), rs.getString(2), rs.getBoolean(3), rs.getString(4)));
                }
            } catch (SQLException sqle) {
                System.err.println("getCartas: "+sqle);
                System.exit(2);
            }
        }
        return mazo;
    }
    
}
