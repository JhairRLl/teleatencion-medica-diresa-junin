/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.model;

import java.time.LocalDate;

/**
 *
 * @author HP
 */
public final class UsuarioFactory {
    
    private UsuarioFactory() {}

    public static Paciente crearPaciente(String idUsuario, String dni, String nombres, String apellidos,
                                         String correo, LocalDate fechaNacimiento, boolean validadoReniec,
                                         String provinciaJunin, String tipoSeguro, boolean coberturaSisActiva) {
        return new Paciente(idUsuario, dni, nombres, apellidos, correo, fechaNacimiento,
                validadoReniec, provinciaJunin, tipoSeguro, coberturaSisActiva);
    }

    public static Medico crearMedico(String idUsuario, String dni, String nombres, String apellidos,
                                     String correo, LocalDate fechaNacimiento, boolean validadoReniec,
                                     String cmp, String especialidad, boolean habilitadoSusalud) {
        return new Medico(idUsuario, dni, nombres, apellidos, correo, fechaNacimiento,
                validadoReniec, cmp, especialidad, habilitadoSusalud);
    }
}
