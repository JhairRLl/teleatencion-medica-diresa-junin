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
public class Paciente extends Usuario {
    
    private String provinciaJunin;
    private String tipoSeguro;
    private boolean coberturaSisActiva;

    public Paciente(String idUsuario, String dni, String nombres, String apellidos, String correo,
                    LocalDate fechaNacimiento, boolean validadoReniec,
                    String provinciaJunin, String tipoSeguro, boolean coberturaSisActiva) {
        super(idUsuario, dni, nombres, apellidos, correo, fechaNacimiento, "PACIENTE", validadoReniec);
        this.provinciaJunin = provinciaJunin;
        this.tipoSeguro = tipoSeguro;
        this.coberturaSisActiva = coberturaSisActiva;
    }

    public boolean verificarCoberturaSIS() {
        return coberturaSisActiva || (tipoSeguro != null && tipoSeguro.toUpperCase().contains("SIS"));
    }

    public String getProvinciaJunin() { return provinciaJunin; }
    public String getTipoSeguro() { return tipoSeguro; }
    public boolean isCoberturaSisActiva() { return coberturaSisActiva; }
}
