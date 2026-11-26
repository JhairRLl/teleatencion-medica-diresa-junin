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
    Usuario autenticarPorDni(String dni);
}
