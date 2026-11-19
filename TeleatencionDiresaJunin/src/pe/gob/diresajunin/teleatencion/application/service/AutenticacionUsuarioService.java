/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.application.service;

import pe.gob.diresajunin.teleatencion.domain.model.Medico;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.port.in.ValidarIdentidadUseCase;
import pe.gob.diresajunin.teleatencion.domain.port.out.ReniecSisPort;
import pe.gob.diresajunin.teleatencion.domain.port.out.UsuarioRepositoryPort;

/**
 *
 * @author HP
 */
public class AutenticacionUsuarioService implements ValidarIdentidadUseCase {
    
    
    private final UsuarioRepositoryPort usuarioRepository;
    private final ReniecSisPort reniecSisPort;

    // Inyección de dependencias por constructor (Arquitectura Hexagonal)
    public AutenticacionUsuarioService(UsuarioRepositoryPort usuarioRepository, ReniecSisPort reniecSisPort) {
        this.usuarioRepository = usuarioRepository;
        this.reniecSisPort = reniecSisPort;
    }

    @Override
    public Usuario autenticarPorDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe contener exactamente 8 dígitos numéricos.");
        }

        // 1. Validación externa contra el servicio RENIEC
        if (!reniecSisPort.validarDniEnReniec(dni)) {
            throw new IllegalStateException("El DNI " + dni + " no se encuentra válido o activo en RENIEC.");
        }

        // 2. Búsqueda en la base de datos de DIRESA Junín (SQL Server)
        Usuario usuario = usuarioRepository.buscarPorDni(dni);
        if (usuario == null) {
            throw new IllegalStateException("El ciudadano con DNI " + dni + " no está registrado en la plataforma.");
        }

        // 3. Reglas específicas según el tipo de usuario (Polimorfismo)
        if (usuario instanceof Paciente) {
            Paciente paciente = (Paciente) usuario;
            boolean sisVigente = reniecSisPort.consultarCoberturaSis(dni);
            paciente.setCoberturaSisActiva(sisVigente);
        } else if (usuario instanceof Medico) {
            Medico medico = (Medico) usuario;
            if (!medico.esMedicoHabilitado()) {
                throw new IllegalStateException("El médico Dr(a). " + medico.getApellidos() + " no está habilitado en SUSALUD.");
            }
        }

        return usuario;
    }
}
