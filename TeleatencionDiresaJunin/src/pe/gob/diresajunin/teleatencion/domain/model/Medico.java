/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.model;

/**
 *
 * @author HP
 */
public class Medico extends Usuario {
    
    // Atributos privados (- en UML)
    private String cmp;
    private String especialidad;
    private boolean habilitadoSusalud;

    public Medico() {
        super();
    }

    public Medico(String idUsuario, String dni, String nombres, String apellidos, String correo,
                  String cmp, String especialidad, boolean habilitadoSusalud) {
        super(idUsuario, dni, nombres, apellidos, correo);
        this.cmp = cmp;
        this.especialidad = especialidad;
        this.habilitadoSusalud = habilitadoSusalud;
    }

    // Validación de habilitación profesional para teleconsulta
    public boolean esMedicoHabilitado() {
        return autenticarDNI() && this.cmp != null && !this.cmp.isEmpty() && this.habilitadoSusalud;
    }

    // Getters y Setters
    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isHabilitadoSusalud() {
        return habilitadoSusalud;
    }

    public void setHabilitadoSusalud(boolean habilitadoSusalud) {
        this.habilitadoSusalud = habilitadoSusalud;
    }

    @Override
    public String toString() {
        return "Medico {" +
                "idUsuario='" + idUsuario + '\'' +
                ", dni='" + dni + '\'' +
                ", nombreCompleto='Dr(a). " + nombres + " " + apellidos + '\'' +
                ", cmp='" + cmp + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", habilitadoSusalud=" + habilitadoSusalud +
                '}';
    }
}
