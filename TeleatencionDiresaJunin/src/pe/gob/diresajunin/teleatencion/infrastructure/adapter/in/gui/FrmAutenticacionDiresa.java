/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gob.diresajunin.teleatencion.infrastructure.adapter.in.gui;

import java.awt.*;
import java.time.format.DateTimeFormatter;
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
    private JTextField txtFechaNacimiento;
    private JLabel lblEstadoValidacion;
    private JLabel lblDetalleExtra;

    private static final Color COLOR_BARRA_NAVEGADOR = new Color(226, 232, 240);
    private static final Color COLOR_NAVBAR_OSCURO = new Color(26, 48, 80);
    private static final Color COLOR_SIDEBAR = new Color(241, 245, 249);
    private static final Color COLOR_AZUL_ACTIVO = new Color(37, 99, 235);
    private static final Color COLOR_VERDE_BTN = new Color(46, 125, 50);
    private static final Color COLOR_VERDE_FONDO = new Color(220, 252, 231);
    private static final Color COLOR_VERDE_TEXTO = new Color(22, 101, 52);
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public FrmAutenticacionDiresa(ValidarIdentidadUseCase validarIdentidadUseCase) {
        this.validarIdentidadUseCase = validarIdentidadUseCase;
        configurarVentana();
        add(crearCabeceraSuperior(), BorderLayout.NORTH);
        add(crearPanelLateral(), BorderLayout.WEST);
        add(crearContenidoCentral(), BorderLayout.CENTER);
    }

    private void configurarVentana() {
        setTitle("HU-01: Autenticación y Validación de Identidad (RF01) — DIRESA Junín");
        setSize(920, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private JPanel crearCabeceraSuperior() {
        JPanel pnlTop = new JPanel(new BorderLayout());

        JPanel pnlBrowser = new JPanel(new BorderLayout(10, 0));
        pnlBrowser.setBackground(COLOR_BARRA_NAVEGADOR);
        pnlBrowser.setBorder(new EmptyBorder(8, 15, 8, 15));
        JTextField txtUrl = new JTextField("https://teleatencion.diresajunin.gob.pe/autenticacion");
        txtUrl.setEditable(false);
        txtUrl.setBackground(Color.WHITE);
        pnlBrowser.add(new JLabel("←  →  ↻"), BorderLayout.WEST);
        pnlBrowser.add(txtUrl, BorderLayout.CENTER);

        JPanel pnlNavbar = new JPanel(new BorderLayout());
        pnlNavbar.setBackground(COLOR_NAVBAR_OSCURO);
        pnlNavbar.setBorder(new EmptyBorder(14, 20, 14, 20));
        JLabel lblTitulo = new JLabel("PLATAFORMA DE TELEATENCIÓN MÉDICA REGIONAL — DIRESA JUNÍN");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 14));
        pnlNavbar.add(lblTitulo, BorderLayout.WEST);

        pnlTop.add(pnlBrowser, BorderLayout.NORTH);
        pnlTop.add(pnlNavbar, BorderLayout.SOUTH);
        return pnlTop;
    }

    private JPanel crearPanelLateral() {
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setLayout(new BoxLayout(pnlSidebar, BoxLayout.Y_AXIS));
        pnlSidebar.setBackground(COLOR_SIDEBAR);
        pnlSidebar.setPreferredSize(new Dimension(210, 0));
        pnlSidebar.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 0, 1, new Color(203, 213, 225)),
                new EmptyBorder(20, 15, 20, 15)));

        JLabel lblRol = new JLabel("TIPO DE USUARIO");
        lblRol.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblRol.setForeground(new Color(100, 116, 139));

        btnMenuPaciente = crearBotonRol("Paciente / Ciudadano", true);
        btnMenuMedico = crearBotonRol("Médico Teleatendedor", false);

        btnMenuPaciente.addActionListener(e -> cambiarRol("PACIENTE"));
        btnMenuMedico.addActionListener(e -> cambiarRol("MEDICO"));

        pnlSidebar.add(lblRol);
        pnlSidebar.add(Box.createVerticalStrut(12));
        pnlSidebar.add(btnMenuPaciente);
        pnlSidebar.add(Box.createVerticalStrut(8));
        pnlSidebar.add(btnMenuMedico);
        return pnlSidebar;
    }

    private JButton crearBotonRol(String texto, boolean activo) {
        JButton btn = new JButton(texto);
        btn.setMaximumSize(new Dimension(180, 38));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(activo ? COLOR_AZUL_ACTIVO : Color.WHITE);
        btn.setForeground(activo ? Color.WHITE : COLOR_NAVBAR_OSCURO);
        return btn;
    }

    private JPanel crearContenidoCentral() {
        JPanel pnlWrapper = new JPanel(new GridBagLayout());
        pnlWrapper.setBackground(Color.WHITE);

        pnlMarcoAutenticacion = new JPanel();
        pnlMarcoAutenticacion.setLayout(new BoxLayout(pnlMarcoAutenticacion, BoxLayout.Y_AXIS));
        pnlMarcoAutenticacion.setBackground(Color.WHITE);
        pnlMarcoAutenticacion.setPreferredSize(new Dimension(550, 430));
        pnlMarcoAutenticacion.setBorder(new CompoundBorder(
                new LineBorder(COLOR_AZUL_ACTIVO, 2, true),
                new EmptyBorder(22, 28, 22, 28)));

        JLabel lblHeader = new JLabel("Validación de Identidad en Tiempo Real (RENIEC / SIS)");
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblHeader.setForeground(COLOR_NAVBAR_OSCURO);

        txtDni = new JTextField();
        txtNombresCompletos = crearCampoSoloLectura();
        txtFechaNacimiento = crearCampoSoloLectura();

        lblEstadoValidacion = new JLabel("Ingrese su DNI de 8 dígitos para validar y acceder");
        lblEstadoValidacion.setOpaque(true);
        lblEstadoValidacion.setBackground(COLOR_SIDEBAR);
        lblEstadoValidacion.setForeground(COLOR_NAVBAR_OSCURO);
        lblEstadoValidacion.setBorder(new EmptyBorder(8, 12, 8, 12));

        lblDetalleExtra = new JLabel("Cobertura SIS / Especialidad: Pendiente de verificación");
        lblDetalleExtra.setFont(new Font("SansSerif", Font.ITALIC, 12));

        pnlMarcoAutenticacion.add(lblHeader);
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(15));
        pnlMarcoAutenticacion.add(crearFilaCampo("Documento Nacional de Identidad (DNI):", txtDni));
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(10));
        pnlMarcoAutenticacion.add(crearFilaCampo("Nombres y Apellidos (RENIEC):", txtNombresCompletos));
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(10));
        pnlMarcoAutenticacion.add(crearFilaCampo("Fecha de Nacimiento:", txtFechaNacimiento));
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(14));
        pnlMarcoAutenticacion.add(lblEstadoValidacion);
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(8));
        pnlMarcoAutenticacion.add(lblDetalleExtra);
        pnlMarcoAutenticacion.add(Box.createVerticalStrut(18));
        pnlMarcoAutenticacion.add(crearPanelBotones());

        pnlWrapper.add(pnlMarcoAutenticacion);
        return pnlWrapper;
    }

    private JTextField crearCampoSoloLectura() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        txt.setBackground(new Color(248, 250, 252));
        return txt;
    }

    private JPanel crearFilaCampo(String etiqueta, JTextField campo) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(490, 52));
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        p.add(lbl, BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private JPanel crearPanelBotones() {
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlBtns.setBackground(Color.WHITE);

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        JButton btnIngresar = new JButton("Validar e Ingresar");
        btnIngresar.setBackground(COLOR_VERDE_BTN);
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnIngresar.addActionListener(e -> ejecutarValidacion());

        pnlBtns.add(btnLimpiar);
        pnlBtns.add(btnIngresar);
        return pnlBtns;
    }

    private void cambiarRol(String nuevoRol) {
        this.rolSeleccionado = nuevoRol;
        boolean esPaciente = "PACIENTE".equals(nuevoRol);
        btnMenuPaciente.setBackground(esPaciente ? COLOR_AZUL_ACTIVO : Color.WHITE);
        btnMenuPaciente.setForeground(esPaciente ? Color.WHITE : COLOR_NAVBAR_OSCURO);
        btnMenuMedico.setBackground(!esPaciente ? COLOR_AZUL_ACTIVO : Color.WHITE);
        btnMenuMedico.setForeground(!esPaciente ? Color.WHITE : COLOR_NAVBAR_OSCURO);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtDni.setText("");
        txtNombresCompletos.setText("");
        txtFechaNacimiento.setText("");
        lblEstadoValidacion.setBackground(COLOR_SIDEBAR);
        lblEstadoValidacion.setForeground(COLOR_NAVBAR_OSCURO);
        lblEstadoValidacion.setText("Ingrese su DNI de 8 dígitos para validar y acceder");
        lblDetalleExtra.setText("Cobertura SIS / Especialidad: Pendiente de verificación");
    }

    private void ejecutarValidacion() {
        try {
            Usuario usuario = validarIdentidadUseCase.autenticarPorDni(txtDni.getText());
            txtNombresCompletos.setText(usuario.getNombres() + " " + usuario.getApellidos());
            txtFechaNacimiento.setText(usuario.getFechaNacimiento().format(FMT_FECHA));

            lblEstadoValidacion.setBackground(COLOR_VERDE_FONDO);
            lblEstadoValidacion.setForeground(COLOR_VERDE_TEXTO);
            lblEstadoValidacion.setText(usuario.isNuevoRegistro()
                    ? "VALIDADO CON RENIEC — NUEVO USUARIO REGISTRADO (" + usuario.getIdUsuario() + ")"
                    : "VALIDADO CON RENIEC — SESIÓN HABILITADA (" + usuario.getIdUsuario() + " - " + usuario.getRol() + ")");

            if (usuario instanceof Paciente) {
                Paciente p = (Paciente) usuario;
                String estadoSis = p.isCoberturaSisActiva() ? "SIS Activo (Exonerado 100%)" : "Sin cobertura SIS (Particular)";
                lblDetalleExtra.setText("Provincia: " + p.getProvinciaJunin() + " | Seguro: " + p.getTipoSeguro() + " — " + estadoSis);
            } else if (usuario instanceof Medico) {
                Medico m = (Medico) usuario;
                lblDetalleExtra.setText("CMP: " + m.getCmp() + " | Especialidad: " + m.getEspecialidad()
                        + (m.isHabilitadoSusalud() ? " (Habilitado SUSALUD)" : " (Inhabilitado)"));
            }
        } catch (Exception ex) {
            lblEstadoValidacion.setBackground(new Color(254, 226, 226));
            lblEstadoValidacion.setForeground(new Color(153, 27, 27));
            lblEstadoValidacion.setText(ex.getMessage());
            txtNombresCompletos.setText("");
            txtFechaNacimiento.setText("");
        }
    }
}
