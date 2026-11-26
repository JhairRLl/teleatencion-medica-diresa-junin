/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.out;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import pe.gob.diresajunin.teleatencion.domain.model.CiudadanoReniecDTO;
import pe.gob.diresajunin.teleatencion.domain.port.out.ReniecSisPort;

/**
 *
 * @author HP
 */
public class ReniecSisMockAdapter implements ReniecSisPort {
    
    private final Map<String, CiudadanoReniecDTO> padronSimulado = new HashMap<>();

    public ReniecSisMockAdapter() {
        padronSimulado.put("71558742", new CiudadanoReniecDTO(
                "71558742", "Luis", "Dionisio Rivera", "luis.dionisio@gmail.com",
                LocalDate.of(1998, 5, 14), "Huancayo", "SIS Gratuito", true));
        padronSimulado.put("73615827", new CiudadanoReniecDTO(
                "73615827", "Cristian", "Guerra Aniceto", "cristian.guerra@gmail.com",
                LocalDate.of(1999, 8, 22), "Chanchamayo", "SIS Independiente", true));
        padronSimulado.put("74902286", new CiudadanoReniecDTO(
                "74902286", "Sayuri", "Marmanilo Soler", "sayuri.marmanilo@gmail.com",
                LocalDate.of(2000, 3, 10), "Tarma", "Particular", false));
        padronSimulado.put("70421351", new CiudadanoReniecDTO(
                "70421351", "Jhair", "Rojas Llactahuaman", "jhair.rojas@diresajunin.gob.pe",
                LocalDate.of(1999, 11, 19), "Huancayo", "EsSalud / CMP", true));
        // Ciudadanos nuevos para probar autoregistro automático en SQL Server (US00005, US00006...)
        padronSimulado.put("72458910", new CiudadanoReniecDTO(
                "72458910", "Valeria", "Huamán Quispe", "valeria.huaman@gmail.com",
                LocalDate.of(1997, 7, 25), "Tarma", "SIS Gratuito", true));
        padronSimulado.put("75123498", new CiudadanoReniecDTO(
                "75123498", "Mateo", "Cárdenas Poma", "mateo.cardenas@gmail.com",
                LocalDate.of(1994, 2, 18), "Jauja", "SIS Independiente", true));
    }

    @Override
    public Optional<CiudadanoReniecDTO> consultarCiudadanoEnReniec(String dni) {
        if (dni == null || !dni.matches("\\d{8}") || dni.startsWith("000")) {
            return Optional.empty();
        }
        if (padronSimulado.containsKey(dni)) {
            return Optional.of(padronSimulado.get(dni));
        }

        // Genera un ciudadano válido de RENIEC para cualquier otro DNI de 8 dígitos
        int sufijo = Integer.parseInt(dni.substring(5));
        String[] provincias = {"Huancayo", "Tarma", "Jauja", "Concepción", "Chanchamayo", "Satipo", "Junín", "Yauli", "Chupaca"};
        String provincia = provincias[sufijo % provincias.length];
        return Optional.of(new CiudadanoReniecDTO(
                dni,
                "Ciudadano " + dni.substring(4),
                "Junín Verificado",
                "ciudadano." + dni + "@gmail.com",
                LocalDate.of(1990 + (sufijo % 15), (sufijo % 12) + 1, (sufijo % 27) + 1),
                provincia,
                "SIS Gratuito",
                true
        ));
    }
}
