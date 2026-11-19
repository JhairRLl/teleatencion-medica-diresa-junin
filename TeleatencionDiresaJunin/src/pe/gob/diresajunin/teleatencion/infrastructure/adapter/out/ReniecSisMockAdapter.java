/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.out;

import java.util.Arrays;
import java.util.List;
import pe.gob.diresajunin.teleatencion.domain.port.out.ReniecSisPort;

/**
 *
 * @author HP
 */
public class ReniecSisMockAdapter implements ReniecSisPort {
    
    // Padrones simulados con los DNIs del equipo de desarrollo (Junín)
    private static final List<String> PADRON_RENIEC_ACTIVO = Arrays.asList(
            "71558742", // Dionisio Rivera Luis
            "73615827", // Guerra Aniceto Cristian
            "74902286", // Marmanilo Soler Sayuri
            "70421351"  // Rojas Llactahuaman Jhair
    );

    private static final List<String> PADRON_SIS_ACTIVO = Arrays.asList(
            "71558742", // Luis - SIS Gratuito
            "73615827"  // Cristian - SIS Independiente
            // Sayuri (74902286) es Particular (sin cobertura SIS activa)
    );

    @Override
    public boolean validarDniEnReniec(String dni) {
        return PADRON_RENIEC_ACTIVO.contains(dni);
    }

    @Override
    public boolean consultarCoberturaSis(String dni) {
        return PADRON_SIS_ACTIVO.contains(dni);
    }
}
