/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.model;

/**
 *
 * @author HP
 */
public class Paciente extends Usuario {
    
    // Atributos privados (- en UML)
    private String provinciaJunin;
    private String tipoSeguro;
    private boolean coberturaSisActiva;

    public Paciente() {
        super();
    }

    public Paciente(String idUsuario, String dni, String nombres, String apellidos, String correo,
                    String provinciaJunin, String tipoSeguro, boolean coberturaSisActiva) {
        super(idUsuario, dni, nombres, apellidos, correo);
        this.provinciaJunin = provinciaJunin;
        this.tipoSeguro = tipoSeguro;
        this.coberturaSisActiva = coberturaSisActiva;
    }

    // Método del diagrama UML (+verificarCoberturaSIS(): Boolean)
    public boolean verificarCoberturaSIS() {
        return this.coberturaSisActiva && this.tipoSeguro != null && this.tipoSeguro.toUpperCase().contains("SIS");
    }

    // Getters y Setters
    public String getProvinciaJunin() {
        return provinciaJunin;
    }

    public void setProvinciaJunin(String provinciaJunin) {
        this.provinciaJunin = provinciaJunin;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public boolean isCoberturaSisActiva() {
        return coberturaSisActiva;
    }

    public void setCoberturaSisActiva(boolean coberturaSisActiva) {
        this.coberturaSisActiva = coberturaSisActiva;
    }

    @Override
    public String toString() {
        return "Paciente {" +
                "idUsuario='" + idUsuario + '\'' +
                ", dni='" + dni + '\'' +
                ", nombreCompleto='" + nombres + " " + apellidos + '\'' +
                ", provinciaJunin='" + provinciaJunin + '\'' +
                ", tipoSeguro='" + tipoSeguro + '\'' +
                ", coberturaSisActiva=" + coberturaSisActiva +
                '}';
    }
}
