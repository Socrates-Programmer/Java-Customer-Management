/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package com.mycompany.gestor_de_ventas;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author Marcos A
 */
public class Cconexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String pass = "Marcos1234";
    String db = "final";
    String ip = "localhost";
    String puerto = "3306";

    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    
    
    public Connection estableceConexion(){
    
    try {
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        conectar = DriverManager.getConnection(cadena, usuario, pass);
        //JOptionPane.showMessageDialog(null,"La conexión se ha realizado con éxito.");
        
    }   catch (Exception e){
                JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos, erro"+ e.toString());
                
    }
        return conectar;
    
    }
    

}
