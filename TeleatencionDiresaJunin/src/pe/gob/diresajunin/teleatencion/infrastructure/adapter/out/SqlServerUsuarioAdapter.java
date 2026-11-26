/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.out;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import pe.gob.diresajunin.teleatencion.domain.model.Medico;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.model.UsuarioFactory;
import pe.gob.diresajunin.teleatencion.domain.port.out.UsuarioRepositoryPort;

/**
 *
 * @author HP
 */
public class SqlServerUsuarioAdapter implements UsuarioRepositoryPort {
    
    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        String sql = "SELECT u.idUsuario, u.dni, u.nombres, u.apellidos, u.correo, u.fecha_nacimiento, "
                   + "p.provinciaJunin, p.tipoSeguro, p.coberturaSisActiva, "
                   + "m.cmp, m.especialidad, m.habilitadoSusalud "
                   + "FROM dbo.tblUsuario u "
                   + "LEFT JOIN dbo.tblPaciente p ON u.idUsuario = p.idUsuario "
                   + "LEFT JOIN dbo.tblMedico m ON u.idUsuario = m.idUsuario "
                   + "WHERE u.dni = ?";

        try (Connection conn = ConexionSQLServer.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String idUsuario = rs.getString("idUsuario");
                    String nombres = rs.getString("nombres");
                    String apellidos = rs.getString("apellidos");
                    String correo = rs.getString("correo");
                    Date sqlDate = rs.getDate("fecha_nacimiento");
                    LocalDate fechaNac = (sqlDate != null) ? sqlDate.toLocalDate() : LocalDate.of(1995, 1, 1);

                    // Si tiene cmp registrado en tblMedico, es MEDICO
                    String cmp = rs.getString("cmp");
                    if (cmp != null) {
                        return Optional.of(UsuarioFactory.crearMedico(
                                idUsuario, dni, nombres, apellidos, correo, fechaNac, true,
                                cmp,
                                rs.getString("especialidad"),
                                rs.getBoolean("habilitadoSusalud")
                        ));
                    }

                    // Caso contrario, es PACIENTE en tblPaciente
                    return Optional.of(UsuarioFactory.crearPaciente(
                            idUsuario, dni, nombres, apellidos, correo, fechaNac, true,
                            rs.getString("provinciaJunin"),
                            rs.getString("tipoSeguro"),
                            rs.getBoolean("coberturaSisActiva")
                    ));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al consultar SQL Server (DIRESA Junín): " + ex.getMessage(), ex);
        }
        return Optional.empty();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        String sqlUsuario = "INSERT INTO dbo.tblUsuario (idUsuario, dni, nombres, apellidos, correo, fecha_nacimiento) "
                          + "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPaciente = "INSERT INTO dbo.tblPaciente (idUsuario, provinciaJunin, tipoSeguro, coberturaSisActiva) "
                           + "VALUES (?, ?, ?, ?)";
        String sqlMedico = "INSERT INTO dbo.tblMedico (idUsuario, cmp, especialidad, habilitadoSusalud) "
                         + "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionSQLServer.obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                String nuevoId = (usuario.getIdUsuario() != null && !usuario.getIdUsuario().isBlank())
                        ? usuario.getIdUsuario()
                        : generarSiguienteIdUsuario(conn);
                usuario.setIdUsuario(nuevoId);

                try (PreparedStatement psUser = conn.prepareStatement(sqlUsuario)) {
                    psUser.setString(1, nuevoId);
                    psUser.setString(2, usuario.getDni());
                    psUser.setString(3, usuario.getNombres());
                    psUser.setString(4, usuario.getApellidos());
                    psUser.setString(5, usuario.getCorreo());
                    psUser.setDate(6, Date.valueOf(usuario.getFechaNacimiento()));
                    psUser.executeUpdate();
                }

                if (usuario instanceof Paciente) {
                    Paciente p = (Paciente) usuario;
                    try (PreparedStatement psPac = conn.prepareStatement(sqlPaciente)) {
                        psPac.setString(1, nuevoId);
                        psPac.setString(2, p.getProvinciaJunin());
                        psPac.setString(3, p.getTipoSeguro());
                        psPac.setBoolean(4, p.isCoberturaSisActiva());
                        psPac.executeUpdate();
                    }
                } else if (usuario instanceof Medico) {
                    Medico m = (Medico) usuario;
                    try (PreparedStatement psMed = conn.prepareStatement(sqlMedico)) {
                        psMed.setString(1, nuevoId);
                        psMed.setString(2, m.getCmp());
                        psMed.setString(3, m.getEspecialidad());
                        psMed.setBoolean(4, m.isHabilitadoSusalud());
                        psMed.executeUpdate();
                    }
                }

                conn.commit();
                return usuario;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Error al registrar usuario en SQL Server: " + ex.getMessage(), ex);
        }
    }

    private String generarSiguienteIdUsuario(Connection conn) throws SQLException {
        String sqlMax = "SELECT MAX(idUsuario) AS maxId FROM dbo.tblUsuario WHERE idUsuario LIKE 'US%'";
        try (PreparedStatement ps = conn.prepareStatement(sqlMax);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                String maxId = rs.getString("maxId");
                if (maxId != null && maxId.length() == 7) {
                    int numero = Integer.parseInt(maxId.substring(2));
                    return String.format("US%05d", numero + 1);
                }
            }
        }
        return "US00001";
    }
}
