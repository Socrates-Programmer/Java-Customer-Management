/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestor_de_ventas;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 *
 * @author Marcos A
 */
public class Cproducto {


    long cedula;
    String vendedor;
    String tipoCompra;
    String artículo;
    String precioT;
    
        public long getCedula() {
            return cedula;
        }

        public void setCedula(long cedula) {
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
                String sql = "SELECT p.fecha, p.vendedor, p.tipo_compra, p.articulo, p.precio_total, c.name, c.cedula FROM producto p INNER JOIN cliente c ON p.cedula = c.cedula ORDER BY c.cedula";
                PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                // Escribir los datos en el archivo de texto
                fw = new FileWriter(file);
                bw = new BufferedWriter(fw);

                String currentCliente = "";
                float totalGastado = 0;

                // Recorrer el conjunto de resultados
                while (resultSet.next()) {
                    String cliente = resultSet.getString("cedula") + " - " + resultSet.getString("name");
                    if (!cliente.equals(currentCliente)) {
                        // Si es un cliente nuevo, escribir su información
                        if (!currentCliente.isEmpty()) {
                            // Escribir el total gastado por el cliente anterior
                            bw.write("Total gastado por el cliente: " + totalGastado + "\n\n");
                            totalGastado = 0; // Reiniciar el total gastado para el nuevo cliente
                        }
                        bw.write("Cedula y nombre: " + cliente + "\n");
                        currentCliente = cliente;
                        bw.write("Tabla:\n");
                        bw.write("Fecha / Vendedor / Tipo Compra / Artículo / Precio\n");
                    }
                    // Agregar detalles del producto
                    bw.write(resultSet.getString("fecha") + " / ");
                    bw.write(resultSet.getString("vendedor") + " / ");
                    bw.write(resultSet.getString("tipo_compra") + " / ");
                    bw.write(resultSet.getString("articulo") + " / ");
                    bw.write(resultSet.getString("precio_total") + "\n");
                    // Actualizar el total gastado por el cliente
                    totalGastado += Float.parseFloat(resultSet.getString("precio_total"));
                }
                // Escribir el total gastado por el último cliente
                bw.write("Total gastado por el cliente: " + totalGastado + "\n");

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

    
    

    //Insertar producto - Star -

         // Insertar producto - Star -
public void insertarproducto(JTextField cedulaParam, JTextField vendedorParam, JComboBox<String> tipoCompraParam, JComboBox<String> artículoParam) {
    String cedulaText = cedulaParam.getText();

    // Validar que el campo cédula tenga exactamente 11 caracteres
    if (cedulaText.length() != 11) {
        JOptionPane.showMessageDialog(null, "La cédula debe tener exactamente 11 caracteres.");
        return;
    }

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

    setVendedor(vendedorParam.getText());
    setTipoCompra((String) tipoCompraParam.getSelectedItem());
    setArtículo((String) artículoParam.getSelectedItem());

    // Asignar precio basado en el artículo seleccionado
    String articuloSeleccionado = (String) artículoParam.getSelectedItem();
    String precio;
    switch (articuloSeleccionado) {
        case "TV":
            precio = "100"; // Precio para TV
            break;
        case "Laptop Gamer":
            precio = "200"; // Precio para Laptop Gamer
            break;
        case "Mouse":
            precio = "300"; // Precio para Mouse
            break;
        case "Teclado":
            precio = "300"; // Precio para Teclado
            break;
        case "Celular":
            precio = "300"; // Precio para Celular
            break;
        // Agregar más casos según sea necesario
        default:
            precio = "0"; // Precio por defecto si no coincide ningún artículo
            break;
    }

    // Asignar el precio seleccionado
    setPrecioT(precio);

    Cconexion objetoConexion = new Cconexion();
    Connection conexion = objetoConexion.estableceConexion();

    try {
        // Verificar si la cédula ya existe en la base de datos cliente
        String consultaExistencia = "SELECT COUNT(*) FROM cliente WHERE cedula = ?";
        PreparedStatement psExistencia = conexion.prepareStatement(consultaExistencia);
        psExistencia.setLong(1, getCedula());
        ResultSet rs = psExistencia.executeQuery();
        rs.next();
        if (rs.getInt(1) == 0) {
            JOptionPane.showMessageDialog(null, "La cédula no existe en la base de datos.");
            return; // Salir del método si la cédula no existe
        }

        // Insertar el nuevo producto en la base de datos
        String consultaInsertarProducto = "INSERT INTO producto (cedula, vendedor, tipo_compra, articulo, precio_total) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psInsertarProducto = conexion.prepareStatement(consultaInsertarProducto);
        psInsertarProducto.setLong(1, getCedula());
        psInsertarProducto.setString(2, getVendedor());
        psInsertarProducto.setString(3, getTipoCompra());
        psInsertarProducto.setString(4, articuloSeleccionado);
        psInsertarProducto.setString(5, precio);

        psInsertarProducto.executeUpdate();
        JOptionPane.showMessageDialog(null, "Se insertó correctamente el producto.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al insertar el producto: " + e.getMessage());
    } finally {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}





  

       public void MostrarProductos(JTable tablaParam) {
    Cconexion objetoConexion = new Cconexion();

    DefaultTableModel modelo = new DefaultTableModel();
    TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
    tablaParam.setRowSorter(OrdenarTabla);

    modelo.addColumn("Fecha");
    modelo.addColumn("Cedula");
    modelo.addColumn("Vendedor");
    modelo.addColumn("TipoCompra");
    modelo.addColumn("Articulo");
    modelo.addColumn("Precio");

    tablaParam.setModel(modelo);

    String sql = "SELECT * FROM producto;";
    String[] datos = new String[6];

    try {
        Statement st = objetoConexion.estableceConexion().createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            datos[0] = rs.getString(2);
            // Aquí obtenemos la cédula como un long y luego la formateamos
            long cedula = rs.getLong(3);
            String cedulaFormateada = String.format("%011d", cedula);
            datos[1] = cedulaFormateada;
            datos[2] = rs.getString(4);
            datos[3] = rs.getString(5);
            datos[4] = rs.getString(6);
            datos[5] = rs.getString(7);

            modelo.addRow(datos);
        }

        tablaParam.setModel(modelo);

    } catch (Exception e) {
        JOptionPane.showConfirmDialog(null, "No se pueden mostrar los datos Error: " + e.toString());
    }
}


    // Mostrar la Tabla - End -
/*
 public void configurarArticuloPrecio(JComboBox<String> artículoParam, JComboBox<String> precioTParam) {
        // Crear un mapa con los artículos y sus precios
        Map<String, String> precios = new HashMap<>();
        precios.put("TV", "1000");
        precios.put("Laptop Gamer", "1500");
        precios.put("Mouse", "50");
        precios.put("Teclado", "80");
        precios.put("Celular", "700");

         artículoParam.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String articuloSeleccionado = (String) artículoParam.getSelectedItem();
          String precio = precios.get(articuloSeleccionado);
          precioTParam.setSelectedItem(precio);
        }
      });

    
    }*/

}



