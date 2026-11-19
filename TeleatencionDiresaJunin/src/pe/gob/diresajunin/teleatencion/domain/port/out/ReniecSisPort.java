/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.domain.port.out;

/**
 *
 * @author HP
 */
public interface ReniecSisPort {
    
    boolean validarDniEnReniec(String dni);

    boolean consultarCoberturaSis(String dni);
}
