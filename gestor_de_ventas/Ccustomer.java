/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_de_ventas;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author Marcos A
 */
public class Ccustomer {
    
    long cedula;
    String nombre;
    String direccion;
    String Genero;
    String estado;
    
    public long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getEstado() {
        return estado;
    }

    //Insertar cliente - Star -
    public void setEstado(String estado) {
        this.estado = estado;
    }

        public void insertarCustomer(JTextField cedulaParam, JTextField nombreParam, JTextField direccionParam, JComboBox<String> GeneroParam, JComboBox<String> estadoParam) {
        
            String cedulaText = cedulaParam.getText();
        
            //System.out.println(cedulaText);
        // Validar que el campo cédula tenga exactamente 11 caracteres
        if (cedulaText.length() != 11) {
            JOptionPane.showMessageDialog(null, "La cédula debe tener exactamente 11 caracteres.");
            return;}

        // Validar que el campo cédula contenga solo números
        if (!cedulaText.matches("\\d+")) {

            JOptionPane.showMessageDialog(null, "La cédula debe contener solo números.");
            return;
        }

      // Convertir cédula a número largo (long)
        try {
            long cedula = Long.parseLong(cedulaText);
            setCedula(cedula);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: la cédula debe ser un número válido.");
            e.printStackTrace();
            return;
        }
       
        setNombre(nombreParam.getText());
        setDireccion(direccionParam.getText());
        setGenero((String) GeneroParam.getSelectedItem());
        setEstado((String) estadoParam.getSelectedItem());

        Cconexion objetoConexion = new Cconexion();
        Connection conexion = objetoConexion.estableceConexion();

        try {
            // Verificar si la cédula ya existe en la base de datos
            String consultaExistencia = "SELECT COUNT(*) FROM cliente WHERE cedula = ?";
            PreparedStatement psExistencia = conexion.prepareStatement(consultaExistencia);
            psExistencia.setLong(1, getCedula());
            ResultSet rs = psExistencia.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "La cédula ya existe.");
                return;  // Salir del método si la cédula ya existe
            }

            // Insertar el nuevo cliente en la base de datos
            String consulta = "INSERT INTO cliente (cedula, name, direccion, genero, estado) VALUES (?, ?, ?, ?, ?);";
            CallableStatement cs = conexion.prepareCall(consulta);

            cs.setLong(1, getCedula());
            cs.setString(2, getNombre());
            cs.setString(3, getDireccion());
            cs.setString(4, getGenero());
            cs.setString(5, getEstado());

            cs.execute();

            //JOptionPane.showMessageDialog(null, "Se insertó correctamente el cliente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente el cliente, error: " + e.toString());
        }
        }
            //Insertar cliente - End -

        // Mostrar la Tabla - Star -
        public void MostrarCustomers (JTable tablaParam){
            
            Cconexion objetoConexion = new Cconexion();

            DefaultTableModel modelo = new DefaultTableModel();

            TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
            tablaParam.setRowSorter(OrdenarTabla);

            String sql = "";

            modelo.addColumn("Cedula");
            modelo.addColumn("Nombres");
            modelo.addColumn("Direccion");
            modelo.addColumn("Genero");
            modelo.addColumn("Estado");


            tablaParam.setModel(modelo);

            sql = "SELECT * FROM cliente;";
            
            String[] datos = new String[6];
            
            Statement st;

                try {
                    st = objetoConexion.estableceConexion().createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    while(rs.next()) {
                        datos[0] = rs.getString(2);
                        datos[1] = rs.getString(3);
                        datos[2] = rs.getString(4);
                        datos[3] = rs.getString(5);
                        datos[4] = rs.getString(6);

                        modelo.addRow(datos);
                    }
                    
                    tablaParam.setModel(modelo);

                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(null, "No se puede mostrar los datos Erro: "+e.toString());
                }

        }}
        // Mostrar la Tabla - End -

