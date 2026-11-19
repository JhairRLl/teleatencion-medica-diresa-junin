/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class ConexionSQLServer {
    
   private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=dbTeleatencionDiresaJunin;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

