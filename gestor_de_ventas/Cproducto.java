/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_de_ventas;

import java.sql.CallableStatement;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Marcos A
 */
public class Cproducto {


    int cedula;
    String vendedor;
    String tipoCompra;
    String artículo;
    String precioT;
    
        public int getCedula() {
            return cedula;
        }

        public void setCedula(int cedula) {
            this.cedula = cedula;
        }

        public String getVendedor() {
            return vendedor;
        }

        public void setVendedor(String vendedor) {
            this.vendedor = vendedor;
        }

        public String getTipoCompra() {
            return tipoCompra;
        }

        public void setTipoCompra(String tipoCompra) {
            this.tipoCompra = tipoCompra;
        }

        public String getArtículo() {
            return artículo;
        }

        public void setArtículo(String artículo) {
            this.artículo = artículo;
        }

        public String getPrecioT() {
            return precioT;
        }

        public void setPrecioT(String precioT) {
            this.precioT = precioT;
        }
    
            public void descargarReportePublico() {
                 descargarReporte();
            }
                                              
     public void descargarReporte() {
        BufferedWriter bw = null;
        FileWriter fw = null;
        Connection conexion = null;

        try {
            // Obtener la ubicación del directorio del escritorio
            String desktopPath = System.getProperty("user.home") + File.separator + "Desktop" + File.separator;

            // Crear el archivo en el directorio del escritorio
            File file = new File(desktopPath + "Reporte_Productos.txt");

            // Establecer conexión con la base de datos
            Cconexion objetoConexion = new Cconexion();
            conexion = objetoConexion.estableceConexion();

            if (conexion != null) {
                // Consulta SQL para obtener los datos de la tabla productos
                String sql = "SELECT * FROM producto";
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                // Escribir los datos en el archivo de texto
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);
                while (resultSet.next()) {
                    String linea = resultSet.getInt("id_producto") + "\t"
                            + resultSet.getString("fecha") + "\t"
                            + resultSet.getString("cedula") + "\t"
                            + resultSet.getString("vendedor") + "\t"
                            + resultSet.getString("tipo_compra") + "\t"
                            + resultSet.getString("articulo") + "\t"
                            + resultSet.getString("precio_total") + "\n";
                    bw.write(linea);
                }

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Reporte generado y descargado con éxito.");
            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    

    //Insertar cliente - Star -

        public void insertarproducto(JTextField cedulaParam, JTextField vendedorParam, JComboBox<String> tipoCompraParam, JTextField artículoParam, JTextField precioTParam) {
        String cedulaText = cedulaParam.getText();
        
        
        // Validar que el campo cédula no tenga más de 11 caracteres
        if (cedulaText.length() > 11) {
            JOptionPane.showMessageDialog(null, "La cédula no puede tener más de 11 caracteres.");
            return;
        }

        // Validar que el campo cédula contenga solo números
        if (!cedulaText.matches("\\d+")) {
            JOptionPane.showMessageDialog(null, "La cédula debe contener solo números.");
            return;
        }
        

        // Convertir cédula a número entero
        try {
            int cedula = Integer.parseInt(cedulaText);
            setCedula(cedula);
        } catch (NumberFormatException e) {
            System.out.println("Error: la cédula debe ser un número válido.");
            e.printStackTrace();
            return;  // Salir del método si la cédula no es válida
        }

        setVendedor(vendedorParam.getText());
        
        setTipoCompra((String) tipoCompraParam.getSelectedItem());
        
        setArtículo(artículoParam.getText());
        
        setPrecioT(precioTParam.getText());

        Cconexion objetoConexion = new Cconexion();
        Connection conexion = objetoConexion.estableceConexion();
        

            try {
                // Verificar si la cédula ya existe en la base de datos
                String consultaExistencia = "SELECT COUNT(*) FROM cliente WHERE cedula = ?";
                PreparedStatement psExistencia = conexion.prepareStatement(consultaExistencia);
                psExistencia.setInt(1, cedula);
                ResultSet rs = psExistencia.executeQuery();
                rs.next();
                if (rs.getInt(1) == 0) {
                    JOptionPane.showMessageDialog(null, "La cédula no existe.");
                    return;  // Salir del método si la cédula no existe
                }

                // Insertar el nuevo producto en la base de datos
                String consultaInsertarProducto = "INSERT INTO producto (cedula, vendedor, tipo_compra, articulo, precio_total) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement psInsertarProducto = conexion.prepareStatement(consultaInsertarProducto);
                psInsertarProducto.setInt(1, cedula);
                // Aquí debes setear los otros parámetros del producto
                psInsertarProducto.executeUpdate();

                JOptionPane.showMessageDialog(null, "Se insertó correctamente el producto.");

                // Cerrar la conexión a la base de datos
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            
            
 

        try {

            // Insertar el nuevo cliente en la base de datos
            String consulta = "INSERT INTO producto (cedula, vendedor, tipo_compra, articulo, precio_total) VALUES (?, ?, ?, ?, ?);";
            CallableStatement cs = conexion.prepareCall(consulta);

            cs.setInt(1, getCedula());
            cs.setString(2, getVendedor());
            cs.setString(3, getTipoCompra());
            cs.setString(4, getArtículo());
            cs.setString(5, getPrecioT());

            cs.execute();


        } catch (Exception e) {
        }
        }
            //Insertar cliente - End -

        // Mostrar la Tabla - Star -
        public void MostrarProductos (JTable tablaParam){
            
            Cconexion objetoConexion = new Cconexion();

            DefaultTableModel modelo = new DefaultTableModel();

            TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
            tablaParam.setRowSorter(OrdenarTabla);

            String sql = "";
            modelo.addColumn("Fecha");
            modelo.addColumn("Cedula");
            modelo.addColumn("Vendedor");
            modelo.addColumn("TipoCompra");
            modelo.addColumn("Articulo");
            modelo.addColumn("Precio");


            tablaParam.setModel(modelo);

            sql = "SELECT * FROM producto;";
            
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
                        datos[5] = rs.getString(7);

                        modelo.addRow(datos);
                    }
                    
                    tablaParam.setModel(modelo);

                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(null, "No se puede mostrar los datos Erro: "+e.toString());
                }

        }}
        // Mostrar la Tabla - End -

