/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.application.service;

import java.util.Optional;
import pe.gob.diresajunin.teleatencion.domain.model.CiudadanoReniecDTO;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.model.UsuarioFactory;
import pe.gob.diresajunin.teleatencion.domain.port.in.ValidarIdentidadUseCase;
import pe.gob.diresajunin.teleatencion.domain.port.out.ReniecSisPort;
import pe.gob.diresajunin.teleatencion.domain.port.out.UsuarioRepositoryPort;
/**
 *
 * @author HP
 */
public class AutenticacionUsuarioService implements ValidarIdentidadUseCase {
    
    
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final ReniecSisPort reniecSisPort;

    public AutenticacionUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                       ReniecSisPort reniecSisPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.reniecSisPort = reniecSisPort;
    }

    @Override
    public Usuario autenticarPorDni(String dni) {
        if (dni == null || !dni.trim().matches("\\d{8}")) {
            throw new IllegalArgumentException("DNI inválido: debe ingresar exactamente 8 dígitos numéricos.");
        }

        String dniLimpio = dni.trim();

        // 1. Validar identidad y cobertura en tiempo real con RENIEC / SIS
        CiudadanoReniecDTO datosReniec = reniecSisPort.consultarCiudadanoEnReniec(dniLimpio)
                .orElseThrow(() -> new SecurityException(
                        "DNI no encontrado en RENIEC, por favor verifique e intente nuevamente"));

        // 2. Buscar en la base de datos regional DIRESA Junín
        Optional<Usuario> usuarioExistente = usuarioRepositoryPort.buscarPorDni(dniLimpio);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setValidadoReniec(true);
            return usuario;
        }

        // 3. Si el ciudadano no está registrado aún en SQL Server, registrarlo automáticamente como Paciente
        Paciente nuevoPaciente = UsuarioFactory.crearPaciente(
                null, // El adaptador SQL genera el siguiente correlativo idUsuario (ej. US00005)
                datosReniec.getDni(),
                datosReniec.getNombres(),
                datosReniec.getApellidos(),
                datosReniec.getCorreo(),
                datosReniec.getFechaNacimiento(),
                true,
                datosReniec.getProvinciaJunin(),
                datosReniec.getTipoSeguro(),
                datosReniec.isCoberturaSisActiva()
        );

        Usuario usuarioGuardado = usuarioRepositoryPort.guardar(nuevoPaciente);
        usuarioGuardado.setNuevoRegistro(true);
        return usuarioGuardado;
    }
}
