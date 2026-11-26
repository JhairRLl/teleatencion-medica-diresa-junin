/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.port.out;

import java.util.Optional;
import pe.gob.diresajunin.teleatencion.domain.model.CiudadanoReniecDTO;

/**
 *
 * @author HP
 */
public interface ReniecSisPort {
    
    Optional<CiudadanoReniecDTO> consultarCiudadanoEnReniec(String dni);
}
