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
public class CiudadanoReniecDTO {
    
    private final String dni;
    private final String nombres;
    private final String apellidos;
    private final String correo;
    private final LocalDate fechaNacimiento;
    private final String provinciaJunin;
    private final String tipoSeguro;
    private final boolean coberturaSisActiva;

    public CiudadanoReniecDTO(String dni, String nombres, String apellidos, String correo,
                              LocalDate fechaNacimiento, String provinciaJunin,
                              String tipoSeguro, boolean coberturaSisActiva) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.provinciaJunin = provinciaJunin;
        this.tipoSeguro = tipoSeguro;
        this.coberturaSisActiva = coberturaSisActiva;
    }

    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getProvinciaJunin() { return provinciaJunin; }
    public String getTipoSeguro() { return tipoSeguro; }
    public boolean isCoberturaSisActiva() { return coberturaSisActiva; }
}
