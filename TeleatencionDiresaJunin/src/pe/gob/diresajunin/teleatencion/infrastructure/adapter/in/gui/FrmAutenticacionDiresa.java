/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.in.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.*;
import pe.gob.diresajunin.teleatencion.domain.model.Medico;
import pe.gob.diresajunin.teleatencion.domain.model.Paciente;
import pe.gob.diresajunin.teleatencion.domain.model.Usuario;
import pe.gob.diresajunin.teleatencion.domain.port.in.ValidarIdentidadUseCase;

/**
 *
 * @author HP
 */
public class FrmAutenticacionDiresa extends JFrame {
    
    private final ValidarIdentidadUseCase validarIdentidadUseCase;
    private String rolSeleccionado = "PACIENTE";

    private JButton btnMenuPaciente;
    private JButton btnMenuMedico;
    private JPanel pnlMarcoAutenticacion;
    private JTextField txtDni;
    private JTextField txtNombresCompletos;
    private JLabel lblEstadoValidacion;
    private JLabel lblDetalleExtra;
    private JButton btnValidarReniec;
    private JButton btnCancelar;

    private final Color COLOR_BARRA_NAVEGADOR = new Color(226, 232, 240);
    private final Color COLOR_NAVBAR_OSCURO = new Color(26, 48, 80);
    private final Color COLOR_SIDEBAR = new Color(241, 245, 249);
    private final Color COLOR_AZUL_ACTIVO = new Color(37, 99, 235);
    private final Color COLOR_VERDE_BTN = new Color(46, 125, 50);
    private final Color COLOR_VERDE_FONDO_ESTADO = new Color(220, 252, 231);
    private final Color COLOR_VERDE_TEXTO_ESTADO = new Color(22, 101, 52);

    public FrmAutenticacionDiresa(ValidarIdentidadUseCase validarIdentidadUseCase) {
        this.validarIdentidadUseCase = validarIdentidadUseCase;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("HU-01: Autenticación y Validación de Identidad (RF01) - DIRESA Junín");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ================= 1. BARRA SUPERIOR + NAVBAR =================
        JPanel pnlTopContainer = new JPanel(new BorderLayout());

        JPanel pnlBrowserBar = new JPanel(new BorderLayout(10, 0));
        pnlBrowserBar.setBackground(COLOR_BARRA_NAVEGADOR);
        pnlBrowserBar.setBorder(new EmptyBorder(8, 15, 8, 15));

        JLabel lblFlechas = new JLabel("←   →   ↻");
        lblFlechas.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblFlechas.setForeground(new Color(71, 85, 105));

        JTextField txtUrl = new JTextField("  https://teleatencion.diresajunin.gob.pe/autenticacion");
        txtUrl.setEditable(false);
        txtUrl.setBackground(Color.WHITE);
        txtUrl.setForeground(new Color(71, 85, 105));
        txtUrl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        txtUrl.setBorder(new LineBorder(new Color(203, 213, 225), 1, true));
        txtUrl.setPreferredSize(new Dimension(0, 28));

        pnlBrowserBar.add(lblFlechas, BorderLayout.WEST);
        pnlBrowserBar.add(txtUrl, BorderLayout.CENTER);

        JPanel pnlNavbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 12));
        pnlNavbar.setBackground(COLOR_NAVBAR_OSCURO);
        pnlNavbar.add(crearItemNavbar("Autenticación", true));
        pnlNavbar.add(crearItemNavbar("Teleconsulta", false));
        pnlNavbar.add(crearItemNavbar("Historial SIS", false));
        pnlNavbar.add(crearItemNavbar("DIRESA Junín", false));

        pnlTopContainer.add(pnlBrowserBar, BorderLayout.NORTH);
        pnlTopContainer.add(pnlNavbar, BorderLayout.SOUTH);
        add(pnlTopContainer, BorderLayout.NORTH);

        // ================= 2. MENÚ LATERAL IZQUIERDO =================
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(COLOR_SIDEBAR);
        pnlSidebar.setPreferredSize(new Dimension(190, 0));
        pnlSidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(203, 213, 225)));

        pnlSidebar.add(Box.createVerticalStrut(20));

        btnMenuPaciente = crearBotonLateral("Paciente", true);
        btnMenuMedico = crearBotonLateral("Médico", false);

        btnMenuPaciente.addActionListener(e -> cambiarRol("PACIENTE"));
        btnMenuMedico.addActionListener(e -> cambiarRol("MEDICO"));

        pnlSidebar.add(btnMenuPaciente);
        pnlSidebar.add(Box.createVerticalStrut(10));
        pnlSidebar.add(btnMenuMedico);

        add(pnlSidebar, BorderLayout.WEST);

        // ================= 3. PANEL CENTRAL =================
        JPanel pnlFondoCentral = new JPanel(new BorderLayout());
        pnlFondoCentral.setBackground(Color.WHITE);
        pnlFondoCentral.setBorder(new EmptyBorder(20, 25, 25, 25));

        pnlMarcoAutenticacion = new JPanel(new BorderLayout());
        pnlMarcoAutenticacion.setBackground(Color.WHITE);
        TitledBorder bordeAzul = BorderFactory.createTitledBorder(
                new LineBorder(COLOR_AZUL_ACTIVO, 2, true),
                " Autenticación Paciente ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(30, 41, 59)
        );
        pnlMarcoAutenticacion.setBorder(BorderFactory.createCompoundBorder(
                bordeAzul,
                new EmptyBorder(15, 25, 20, 25)
        ));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 12));

        // --- PESTAÑA 1: INICIO ---
        JPanel pnlTabInicio = new JPanel(new BorderLayout(0, 18));
        pnlTabInicio.setBackground(Color.WHITE);
        pnlTabInicio.setBorder(new EmptyBorder(15, 5, 5, 5));

        JPanel pnlCardFormulario = new JPanel(new GridBagLayout());
        pnlCardFormulario.setBackground(new Color(248, 250, 252));
        pnlCardFormulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1, true),
                new EmptyBorder(18, 22, 18, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSubtitulo = new JLabel("Formulario de datos:");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblSubtitulo.setForeground(new Color(30, 41, 59));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        pnlCardFormulario.add(lblSubtitulo, gbc);

        gbc.gridwidth = 1;

        // Fila 1: DNI
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.35;
        JLabel lblDni = new JLabel("Documento de Identidad (DNI)*:");
        lblDni.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblDni.setForeground(new Color(30, 41, 59));
        pnlCardFormulario.add(lblDni, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        txtDni = new JTextField();
        txtDni.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtDni.setForeground(Color.BLACK);
        txtDni.setBackground(Color.WHITE);
        txtDni.setPreferredSize(new Dimension(260, 34));
        pnlCardFormulario.add(txtDni, gbc);

        // Fila 2: Nombres Completos (AHORA EDITABLE)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.35;
        JLabel lblNombres = new JLabel("Nombres Completos:");
        lblNombres.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblNombres.setForeground(new Color(30, 41, 59));
        pnlCardFormulario.add(lblNombres, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        txtNombresCompletos = new JTextField();
        txtNombresCompletos.setEditable(true);
        txtNombresCompletos.setBackground(Color.WHITE);
        txtNombresCompletos.setForeground(Color.BLACK);
        txtNombresCompletos.setFont(new Font("SansSerif", Font.BOLD, 13));
        txtNombresCompletos.setPreferredSize(new Dimension(260, 34));
        pnlCardFormulario.add(txtNombresCompletos, gbc);

        // Fila 3: Estado de Validación
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.35;
        JLabel lblEstadoTit = new JLabel("Estado de Validación:");
        lblEstadoTit.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstadoTit.setForeground(new Color(30, 41, 59));
        pnlCardFormulario.add(lblEstadoTit, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        lblEstadoValidacion = new JLabel("  [ Pendiente de Verificación ]");
        lblEstadoValidacion.setOpaque(true);
        lblEstadoValidacion.setBackground(new Color(241, 245, 249));
        lblEstadoValidacion.setForeground(new Color(71, 85, 105));
        lblEstadoValidacion.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblEstadoValidacion.setBorder(new LineBorder(new Color(203, 213, 225), 1, true));
        lblEstadoValidacion.setPreferredSize(new Dimension(260, 34));
        pnlCardFormulario.add(lblEstadoValidacion, gbc);

        // Fila 4: Detalle SIS / CMP SUSALUD
        gbc.gridx = 1;
        gbc.gridy = 4;
        lblDetalleExtra = new JLabel(" ");
        lblDetalleExtra.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblDetalleExtra.setForeground(new Color(30, 64, 175));
        pnlCardFormulario.add(lblDetalleExtra, gbc);

        pnlTabInicio.add(pnlCardFormulario, BorderLayout.CENTER);

        // Botones inferiores con pintado sólido forzado para Windows
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        pnlBotones.setBackground(Color.WHITE);

        btnValidarReniec = crearBotonSolido("Validar con RENIEC", COLOR_VERDE_BTN, Color.WHITE, 185, 38);
        btnValidarReniec.addActionListener(this::ejecutarValidacionReniec);

        btnCancelar = crearBotonSolido("Cancelar", new Color(226, 232, 240), new Color(30, 41, 59), 125, 38);
        btnCancelar.addActionListener(e -> limpiarFormulario());

        pnlBotones.add(btnValidarReniec);
        pnlBotones.add(btnCancelar);

        pnlTabInicio.add(pnlBotones, BorderLayout.SOUTH);

        // --- PESTAÑA 2: AYUDA ---
        JTextArea txtAyuda = new JTextArea(
                "GUÍA DE AUTENTICACIÓN - DIRESA JUNÍN (HU-01)\n\n"
                + "1. Seleccione en el panel izquierdo si ingresará como Paciente o Médico.\n"
                + "2. Ingrese su número de DNI de 8 dígitos.\n"
                + "3. Presione 'Validar con RENIEC' para verificar su identidad y cobertura SIS o habilitación CMP."
        );
        txtAyuda.setEditable(false);
        txtAyuda.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtAyuda.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- PESTAÑA 3: TÉRMINOS Y CONDICIONES ---
        JTextArea txtTerminos = new JTextArea(
                "TÉRMINOS DE USO Y PROTECCIÓN DE DATOS PERSONALES (LEY N° 29733)\n\n"
                + "La Dirección Regional de Salud Junín (DIRESA Junín) valida su identidad únicamente\n"
                + "para fines de teleorientación y telemonitoreo médico regional."
        );
        txtTerminos.setEditable(false);
        txtTerminos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtTerminos.setBorder(new EmptyBorder(15, 15, 15, 15));

        tabbedPane.addTab("Inicio", pnlTabInicio);
        tabbedPane.addTab("Ayuda", txtAyuda);
        tabbedPane.addTab("Términos y condiciones", txtTerminos);

        pnlMarcoAutenticacion.add(tabbedPane, BorderLayout.CENTER);
        pnlFondoCentral.add(pnlMarcoAutenticacion, BorderLayout.CENTER);
        add(pnlFondoCentral, BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnValidarReniec);
    }

    private JButton crearBotonSolido(String texto, Color fondo, Color textoColor, int ancho, int alto) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(new Dimension(ancho, alto));
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setForeground(textoColor);
        btn.setBackground(fondo);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JLabel crearItemNavbar(String texto, boolean activo) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(activo ? Color.WHITE : new Color(203, 213, 225));
        lbl.setFont(new Font("SansSerif", activo ? Font.BOLD : Font.PLAIN, 13));
        return lbl;
    }

    private JButton crearBotonLateral(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(160, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aplicarEstiloBotonLateral(btn, activo);
        return btn;
    }

    private void aplicarEstiloBotonLateral(JButton btn, boolean activo) {
        if (activo) {
            btn.setBackground(COLOR_AZUL_ACTIVO);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(226, 232, 240));
            btn.setForeground(new Color(30, 41, 59));
        }
    }

    private void cambiarRol(String nuevoRol) {
        this.rolSeleccionado = nuevoRol;
        boolean esPaciente = "PACIENTE".equals(nuevoRol);

        aplicarEstiloBotonLateral(btnMenuPaciente, esPaciente);
        aplicarEstiloBotonLateral(btnMenuMedico, !esPaciente);

        TitledBorder borde = (TitledBorder) ((CompoundBorder) pnlMarcoAutenticacion.getBorder()).getOutsideBorder();
        borde.setTitle(esPaciente ? " Autenticación Paciente " : " Autenticación Médico ");
        pnlMarcoAutenticacion.repaint();

        limpiarFormulario();
    }

    private void ejecutarValidacionReniec(ActionEvent e) {
        String dni = txtDni.getText().trim();
        try {
            Usuario usuario = validarIdentidadUseCase.autenticarPorDni(dni);

            if ("PACIENTE".equals(rolSeleccionado) && !(usuario instanceof Paciente)) {
                throw new IllegalStateException("El DNI ingresado pertenece a un Médico. Seleccione 'Médico' en el menú izquierdo.");
            }
            if ("MEDICO".equals(rolSeleccionado) && !(usuario instanceof Medico)) {
                throw new IllegalStateException("El DNI ingresado pertenece a un Paciente. Seleccione 'Paciente' en el menú izquierdo.");
            }

            txtNombresCompletos.setText((usuario.getNombres() + " " + usuario.getApellidos()).toUpperCase());
            lblEstadoValidacion.setText("  VALIDADO CON RENIEC");
            lblEstadoValidacion.setBackground(COLOR_VERDE_FONDO_ESTADO);
            lblEstadoValidacion.setForeground(COLOR_VERDE_TEXTO_ESTADO);
            lblEstadoValidacion.setBorder(new LineBorder(new Color(34, 197, 94), 1, true));

            if (usuario instanceof Paciente) {
                Paciente p = (Paciente) usuario;
                lblDetalleExtra.setText("Provincia: " + p.getProvinciaJunin() + " | Seguro: " + p.getTipoSeguro()
                        + (p.verificarCoberturaSIS() ? " (SIS Activo)" : " (Tarifa Particular)"));
            } else if (usuario instanceof Medico) {
                Medico m = (Medico) usuario;
                lblDetalleExtra.setText("CMP: " + m.getCmp() + " | Especialidad: " + m.getEspecialidad() + " | SUSALUD: HABILITADO");
            }

        } catch (Exception ex) {
            lblEstadoValidacion.setText("  NO VALIDADO / ERROR");
            lblEstadoValidacion.setBackground(new Color(254, 226, 226));
            lblEstadoValidacion.setForeground(new Color(185, 28, 28));
            lblEstadoValidacion.setBorder(new LineBorder(new Color(239, 68, 68), 1, true));
            lblDetalleExtra.setText(" ");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación RENIEC - DIRESA Junín", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombresCompletos.setText("");
        lblEstadoValidacion.setText("  [ Pendiente de Verificación ]");
        lblEstadoValidacion.setBackground(new Color(241, 245, 249));
        lblEstadoValidacion.setForeground(new Color(71, 85, 105));
        lblEstadoValidacion.setBorder(new LineBorder(new Color(203, 213, 225), 1, true));
        lblDetalleExtra.setText(" ");
        txtDni.requestFocus();
    }
}
