/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pe.gob.diresajunin.teleatencion;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import pe.gob.diresajunin.teleatencion.application.service.AutenticacionUsuarioService;
import pe.gob.diresajunin.teleatencion.domain.port.in.ValidarIdentidadUseCase;
import pe.gob.diresajunin.teleatencion.infrastructure.adapter.in.gui.FrmAutenticacionDiresa;
import pe.gob.diresajunin.teleatencion.infrastructure.adapter.out.ReniecSisMockAdapter;
import pe.gob.diresajunin.teleatencion.infrastructure.adapter.out.SqlServerUsuarioAdapter;

/**
 *
 * @author HP
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        // Ensamblaje Hexagonal: Adaptadores de Salida -> Servicio de Aplicación -> Adaptador de Entrada GUI
        ValidarIdentidadUseCase autenticacionUseCase = new AutenticacionUsuarioService(
                new SqlServerUsuarioAdapter(),
                new ReniecSisMockAdapter()
        );

        SwingUtilities.invokeLater(() -> {
            FrmAutenticacionDiresa ventana = new FrmAutenticacionDiresa(autenticacionUseCase);
            ventana.setVisible(true);
        });
    }
}
