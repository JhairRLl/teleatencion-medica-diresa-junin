/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.application.service;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.port.out.UsuarioRepositoryPort;
import pe.gob.diresajunin.teleatencion.infrastructure.adapter.out.ReniecSisMockAdapter;

/**
 *
 * @author HP
 */
public class AutenticacionUsuarioServiceTest {
    
    private AutenticacionUsuarioService service;
    private Map<String, Usuario> baseDatosMemoria;

    @Before
    public void setUp() {
        baseDatosMemoria = new HashMap<>();
        baseDatosMemoria.put("71558742", new Paciente(
                "US00001", "71558742", "Luis", "Dionisio Rivera", "luis.dionisio@gmail.com",
                LocalDate.of(1998, 5, 14), true, "Huancayo", "SIS Gratuito", true));

        UsuarioRepositoryPort repoFake = new UsuarioRepositoryPort() {
            @Override
            public Optional<Usuario> buscarPorDni(String dni) {
                return Optional.ofNullable(baseDatosMemoria.get(dni));
            }

            @Override
            public Usuario guardar(Usuario usuario) {
                usuario.setIdUsuario("US00005");
                baseDatosMemoria.put(usuario.getDni(), usuario);
                return usuario;
            }
        };

        service = new AutenticacionUsuarioService(repoFake, new ReniecSisMockAdapter());
    }

    @Test
    public void deberiaAutenticarUsuarioExistenteEnBaseDeDatos() {
        Usuario resultado = service.autenticarPorDni("71558742");
        assertEquals("US00001", resultado.getIdUsuario());
        assertEquals("Luis", resultado.getNombres());
        assertFalse(resultado.isNuevoRegistro());
    }

    @Test
    public void deberiaRegistrarYGuardarAutomaticamenteNuevoCiudadanoValidoEnReniec() {
        Usuario resultado = service.autenticarPorDni("72458910");
        assertTrue(resultado.isNuevoRegistro());
        assertEquals("US00005", resultado.getIdUsuario());
        assertTrue(baseDatosMemoria.containsKey("72458910"));
    }

    @Test
    public void deberiaLanzarErrorCuandoDniNoExisteEnReniec() {
        try {
            service.autenticarPorDni("00012345");
            fail("Debió lanzar SecurityException cuando el DNI no existe en RENIEC");
        } catch (SecurityException ex) {
            assertEquals("DNI no encontrado en RENIEC, por favor verifique e intente nuevamente", ex.getMessage());
        }
    }
}
