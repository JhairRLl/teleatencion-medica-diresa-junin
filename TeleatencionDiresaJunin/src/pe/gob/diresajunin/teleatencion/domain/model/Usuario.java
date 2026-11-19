/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.model;

/**
 *
 * @author HP
 */
public abstract class Usuario {
    
    // Atributos protegidos (# en UML) para ser heredados por Paciente y Medico
    protected String idUsuario;
    protected String dni;
    protected String nombres;
    protected String apellidos;
    protected String correo;

    public Usuario() {
    }

    public Usuario(String idUsuario, String dni, String nombres, String apellidos, String correo) {
        this.idUsuario = idUsuario;
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
    }

    // Método del diagrama UML (+autenticarDNI(): Boolean)
    public boolean autenticarDNI() {
        return this.dni != null && this.dni.matches("\\d{8}");
    }

    // Getters y Setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
