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
public abstract class Usuario {
    
    protected String idUsuario;
    protected String dni;
    protected String nombres;
    protected String apellidos;
    protected String correo;
    protected LocalDate fechaNacimiento;
    protected String rol;
    protected boolean validadoReniec;
    protected boolean nuevoRegistro;

    public Usuario(String idUsuario, String dni, String nombres, String apellidos, String correo,
                   LocalDate fechaNacimiento, String rol, boolean validadoReniec) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe contener exactamente 8 dígitos numéricos.");
        }
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
        this.validadoReniec = validadoReniec;
        this.nuevoRegistro = false;
    }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getRol() { return rol; }
    public boolean isValidadoReniec() { return validadoReniec; }
    public void setValidadoReniec(boolean validadoReniec) { this.validadoReniec = validadoReniec; }
    public boolean isNuevoRegistro() { return nuevoRegistro; }
    public void setNuevoRegistro(boolean nuevoRegistro) { this.nuevoRegistro = nuevoRegistro; }
}