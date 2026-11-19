/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.port.in;

import pe.gob.diresajunin.teleatencion.domain.model.Usuario;

/**
 *
 * @author HP
 */
public interface ValidarIdentidadUseCase {
    /**
     * Autentica y valida la identidad de un usuario (Paciente o Médico)
     * mediante su número de DNI de 8 dígitos (HU-01 / RF01).
     */
    Usuario autenticarPorDni(String dni);
}
