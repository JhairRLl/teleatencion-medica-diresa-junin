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
public class Medico extends Usuario {
    
    private String cmp;
    private String especialidad;
    private boolean habilitadoSusalud;

    public Medico(String idUsuario, String dni, String nombres, String apellidos, String correo,
                  LocalDate fechaNacimiento, boolean validadoReniec,
                  String cmp, String especialidad, boolean habilitadoSusalud) {
        super(idUsuario, dni, nombres, apellidos, correo, fechaNacimiento, "MEDICO", validadoReniec);
        this.cmp = cmp;
        this.especialidad = especialidad;
        this.habilitadoSusalud = habilitadoSusalud;
    }

    public String getCmp() { return cmp; }
    public String getEspecialidad() { return especialidad; }
    public boolean isHabilitadoSusalud() { return habilitadoSusalud; }
}
