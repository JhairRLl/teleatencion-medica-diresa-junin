/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.out;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.gob.diresajunin.teleatencion.domain.model.Medico;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.port.out.UsuarioRepositoryPort;

/**
 *
 * @author HP
 */
public class SqlServerUsuarioAdapter implements UsuarioRepositoryPort {
    
    @Override
    public Usuario buscarPorDni(String dni) {
        String sql = "SELECT u.idUsuario, u.dni, u.nombres, u.apellidos, u.correo, "
                   + "p.provinciaJunin, p.tipoSeguro, p.coberturaSisActiva, "
                   + "m.cmp, m.especialidad, m.habilitadoSusalud "
                   + "FROM tblUsuario u "
                   + "LEFT JOIN tblPaciente p ON u.idUsuario = p.idUsuario "
                   + "LEFT JOIN tblMedico m ON u.idUsuario = m.idUsuario "
                   + "WHERE u.dni = ?";

        try (Connection cn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String idUsuario = rs.getString("idUsuario");
                    String nombres = rs.getString("nombres");
                    String apellidos = rs.getString("apellidos");
                    String correo = rs.getString("correo");

                    // Si tiene datos en tblPaciente, construimos un Paciente
                    if (rs.getString("provinciaJunin") != null) {
                        return new Paciente(
                                idUsuario, dni, nombres, apellidos, correo,
                                rs.getString("provinciaJunin"),
                                rs.getString("tipoSeguro"),
                                rs.getBoolean("coberturaSisActiva")
                        );
                    }

                    // Si tiene datos en tblMedico, construimos un Medico
                    if (rs.getString("cmp") != null) {
                        return new Medico(
                                idUsuario, dni, nombres, apellidos, correo,
                                rs.getString("cmp"),
                                rs.getString("especialidad"),
                                rs.getBoolean("habilitadoSusalud")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar SQL Server: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        String sqlUsuario = "INSERT INTO tblUsuario (idUsuario, dni, nombres, apellidos, correo) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sqlUsuario)) {
            ps.setString(1, usuario.getIdUsuario());
            ps.setString(2, usuario.getDni());
            ps.setString(3, usuario.getNombres());
            ps.setString(4, usuario.getApellidos());
            ps.setString(5, usuario.getCorreo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar usuario en SQL Server: " + e.getMessage(), e);
        }
    }
}
