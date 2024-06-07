/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestor_de_ventas;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * Documento Main
 */
public class Gestor_de_ventas {

    public static void main(String[] args) {
        
        formCustomer objetoFormulario = new formCustomer();
        objetoFormulario.setVisible(true);
        
        formProductos objetoProducto = new formProductos();
        objetoProducto.setVisible(true);

    }
    
}
