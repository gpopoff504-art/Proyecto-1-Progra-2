/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package battleship;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleshipGUI extends JFrame {
    
    private Battleship game;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private static final String LOGIN = "LOGIN";
    private static final String MENU = "MENU";
    private static final String COLOCAR_BARCOS = "COLOCAR_BARCOS";
    private static final String JUEGO = "JUEGO";
    private static final String CONFIG = "CONFIG";
    private static final String PERFIL = "PERFIL";
    private static final String REPORTES = "REPORTES";
    
    private JButton[][] botonesColocar;
    private String[] tiposBarcos = {"PA", "AZ", "SM", "DT"};
    private int barcoActualIndex = 0;
    private boolean esHorizontal = false;
    private JLabel lblInstrucciones;
    
    private Tablero tableroTemp;
    private String usernameJugador2;
    private Player playerJugador2;
    private boolean turnoj1 = true;
    
    private JButton[][] botonesJuego;
    private JLabel lblTurnoJuego;
    private JLabel lblInfo;
    private boolean turnoJ1Juego = true;
    
    private JLabel lblPerfilUser, lblPerfilPass, lblPerfilPuntos;
    private JTextArea txtLogs, txtRanking;
    
    public BattleshipGUI() {
        game = new Battleship();
        
        game.crearJugador("Player1", "123");
        game.crearJugador("Player2", "456");
        
        setTitle("Battleship Dinamico");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(crearPanelLogin(), LOGIN);
        mainPanel.add(crearPanelMenu(), MENU);
        mainPanel.add(crearPanelColocarBarcos(), COLOCAR_BARCOS);
        mainPanel.add(crearPanelJuego(), JUEGO);
        mainPanel.add(crearPanelConfiguracion(), CONFIG);
        mainPanel.add(crearPanelPerfil(), PERFIL);
        mainPanel.add(crearPanelReportes(), REPORTES);
        
        add(mainPanel);
        cardLayout.show(mainPanel, LOGIN);
        
        setVisible(true);
    }
    
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JLabel lblTitulo = new JLabel("BATTLESHIP DINAMICO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(0, 0, 204));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblUser = new JLabel("Username:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JTextField txtUser = new JTextField(20);
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel lblPass = new JLabel("Password:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setFont(new Font("Arial", Font.PLAIN, 14));
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblUser, gbc);
        gbc.gridx = 1;
        panelForm.add(txtUser, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(lblPass, gbc);
        gbc.gridx = 1;
        panelForm.add(txtPass, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(0, 0, 204));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        
        JButton btnLogin = new JButton("LOGIN");
        JButton btnCrear = new JButton("CREAR CUENTA");
        JButton btnSalir = new JButton("SALIR");
        
        estilizarBoton(btnLogin, new Color(46, 204, 113));
        estilizarBoton(btnCrear, new Color(52, 152, 219));
        estilizarBoton(btnSalir, new Color(231, 76, 60));
        
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Llena todos los campos");
                return;
            }
            
            if (game.login(user, pass)) {
                txtUser.setText("");
                txtPass.setText("");
                cardLayout.show(mainPanel, MENU);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o password incorrecta");
            }
        });
        
        btnCrear.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Llena todos los campos");
                return;
            }
            
            if (game.crearJugador(user, pass)) {
                JOptionPane.showMessageDialog(this, "Cuenta creada! Puedes login");
                txtPass.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ese username ya existe");
            }
        });
        
        btnSalir.addActionListener(e -> System.exit(0));
        
        panelBotones.add(btnLogin);
        panelBotones.add(btnCrear);
        panelBotones.add(btnSalir);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelForm, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JLabel lblBienvenida = new JLabel("MENU PRINCIPAL", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 28));
        lblBienvenida.setForeground(Color.WHITE);
        lblBienvenida.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 0, 15));
        panelBotones.setBackground(new Color(0, 0, 204));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 150, 80, 150));
        
        JButton btnJugar = new JButton("JUGAR BATTLESHIP");
        JButton btnConfig = new JButton("CONFIGURACION");
        JButton btnReportes = new JButton("REPORTES");
        JButton btnPerfil = new JButton("MI PERFIL");
        JButton btnCerrar = new JButton("CERRAR SESION");
        
        estilizarBotonMenu(btnJugar);
        estilizarBotonMenu(btnConfig);
        estilizarBotonMenu(btnReportes);
        estilizarBotonMenu(btnPerfil);
        estilizarBotonMenu(btnCerrar);
        
        btnJugar.addActionListener(e -> iniciarJuego());
        
        btnConfig.addActionListener(e -> cardLayout.show(mainPanel, CONFIG));
        
        btnReportes.addActionListener(e -> {
            actualizarReportes();
            cardLayout.show(mainPanel, REPORTES);
        });
        
        btnPerfil.addActionListener(e -> {
            actualizarPerfil();
            cardLayout.show(mainPanel, PERFIL);
        });
        
        btnCerrar.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(this, 
                        "Seguro que deseas cerrar sesion?", 
                        "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                cardLayout.show(mainPanel, LOGIN);
            }
        });
        
        panelBotones.add(btnJugar);
        panelBotones.add(btnConfig);
        panelBotones.add(btnReportes);
        panelBotones.add(btnPerfil);
        panelBotones.add(btnCerrar);
        
        panel.add(lblBienvenida, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelColocarBarcos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 0, 204));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel lblTitulo = new JLabel("COLOCA TUS BARCOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
                
        lblInstrucciones = new JLabel("", SwingConstants.CENTER);
        lblInstrucciones.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInstrucciones.setForeground(Color.CYAN);
        
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblInstrucciones, BorderLayout.SOUTH);
        
        JPanel panelTablero = new JPanel(new GridBagLayout());
        panelTablero.setBackground(new Color(0, 0, 204));
        
        JPanel gridTablero = new JPanel(new GridLayout(8, 8, 3, 3));
        gridTablero.setBackground(Color.BLACK);
        gridTablero.setPreferredSize(new Dimension(480, 480));
        
        botonesColocar = new JButton[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = new JButton("~");
                btn.setFont(new Font("Monospaced", Font.BOLD, 18));
                btn.setBackground(new Color(30, 144, 255));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                
                final int fila = i;
                final int col = j;
                
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        if (btn.getText().equals("~")) {
                            btn.setBackground(new Color(51, 204, 255));
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent evt) {
                        if (btn.getText().equals("~")) {
                            btn.setBackground(new Color(30, 144, 255));
                        }
                    }
                                       
                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        if (SwingUtilities.isRightMouseButton(evt)) {
                            esHorizontal = !esHorizontal;
                            actualizarInstrucciones();
                        }
                    }
                });
                
                btn.addActionListener(e -> colocarBarco(fila, col));
                
                botonesColocar[i][j] = btn;
                gridTablero.add(btn);
            }
        }
        
        panelTablero.add(gridTablero);
        
        JPanel panelInferior = new JPanel(new FlowLayout());
        panelInferior.setBackground(new Color(0, 0, 204));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JButton btnCambiarOrientacion = new JButton("Cambiar Orientacion");
        JButton btnReiniciar = new JButton("Reiniciar");
        
        estilizarBoton(btnCambiarOrientacion, new Color(0, 255, 51));
        estilizarBoton(btnReiniciar, new Color(255, 0, 0));
        
        btnCambiarOrientacion.addActionListener(e -> {
            esHorizontal = !esHorizontal;
            actualizarInstrucciones();
        });
        
        btnReiniciar.addActionListener(e -> reiniciarColocacion());
        
        panelInferior.add(btnCambiarOrientacion);
        panelInferior.add(btnReiniciar);
        
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelTablero, BorderLayout.CENTER);
        panel.add(panelInferior, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelJuego() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0, 0, 204));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
        
        lblTurnoJuego = new JLabel("", SwingConstants.CENTER);
        lblTurnoJuego.setFont(new Font("Arial", Font.BOLD, 26));
        lblTurnoJuego.setForeground(Color.YELLOW);
        
        lblInfo = new JLabel("", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblInfo.setForeground(Color.WHITE);
        
        JPanel panelLabels = new JPanel(new GridLayout(2, 1));
        panelLabels.setBackground(new Color(0, 0, 204));
        panelLabels.add(lblTurnoJuego);
        panelLabels.add(lblInfo);
        
        JButton btnSalir = new JButton("Salir");
        estilizarBoton(btnSalir, new Color(255, 0, 0));
btnSalir.addActionListener(e -> {
    int opcion = JOptionPane.showConfirmDialog(this, 
                "Seguro? Perdera la partida", 
                "Confirmar", JOptionPane.YES_NO_OPTION);
    if (opcion == JOptionPane.YES_OPTION) {
        
        Player abandonador;
        Player ganador;
        
        if (turnoJ1Juego) {
            abandonador = game.getCurrentUser();
            ganador = playerJugador2;
        } else {
            abandonador = playerJugador2;
            ganador = game.getCurrentUser();
        }
        
        ganador.addPoints(3);
        String log = ganador.getUsername() + " gano porque " + 
                     abandonador.getUsername() + " abandono la partida";
        ganador.addLog(log);
        abandonador.addLog(log);
        
        JOptionPane.showMessageDialog(this, 
            ganador.getUsername() + " gana por abandono!");
        
        cardLayout.show(mainPanel, MENU);
    }
});
        
        panelSuperior.add(panelLabels, BorderLayout.CENTER);
        panelSuperior.add(btnSalir, BorderLayout.EAST);
        
        JPanel panelTablero = new JPanel(new GridBagLayout());
        panelTablero.setBackground(new Color(0, 0, 204));
        
        JPanel gridTablero = new JPanel(new GridLayout(8, 8, 3, 3));
        gridTablero.setBackground(Color.BLACK);
        gridTablero.setPreferredSize(new Dimension(500, 500));
        
        botonesJuego = new JButton[8][8];
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton btn = new JButton("~");
                btn.setFont(new Font("Monospaced", Font.BOLD, 20));
                btn.setBackground(new Color(30, 144, 255));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                
                final int fila = i;
                final int col = j;
                
                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent evt) {
                        if (btn.getText().equals("~")) {
                            btn.setBackground(new Color(51, 204, 255));
                        }
                    }
                    
                    @Override
                    public void mouseExited(MouseEvent evt) {
                        if (btn.getText().equals("~")) {
                            btn.setBackground(new Color(30, 144, 255));
                        }
                    }
                });
                
                btn.addActionListener(e -> bombardear(fila, col));
                
                botonesJuego[i][j] = btn;
                gridTablero.add(btn);
            }
        }
        
        panelTablero.add(gridTablero);
        
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelTablero, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelConfiguracion() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JLabel lblTitulo = new JLabel("CONFIGURACION", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setBackground(new Color(0, 0, 204));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblDif = new JLabel("Dificultad:");
        lblDif.setForeground(Color.WHITE);
        lblDif.setFont(new Font("Arial", Font.BOLD, 18));
        
        String[] difs = {"Easy (5 barcos)", "Normal (4 barcos)", "Expert (2 barcos)", "Genius (1 barco)"};
        JComboBox<String> comboDif = new JComboBox<>(difs);
        comboDif.setSelectedIndex(game.getDificultad() - 1);
        comboDif.setFont(new Font("Arial", Font.PLAIN, 16));
        
        JLabel lblModo = new JLabel("Modo:");
        lblModo.setForeground(Color.WHITE);
        lblModo.setFont(new Font("Arial", Font.BOLD, 18));
        
        String[] modos = {"Tutorial (muestra barcos)", "Arcade (oculta barcos)"};
        JComboBox<String> comboModo = new JComboBox<>(modos);
        comboModo.setSelectedIndex(game.getModo() - 1);
        comboModo.setFont(new Font("Arial", Font.PLAIN, 16));
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelCentro.add(lblDif, gbc);
        gbc.gridx = 1;
        panelCentro.add(comboDif, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelCentro.add(lblModo, gbc);
        gbc.gridx = 1;
        panelCentro.add(comboModo, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(0, 0, 204));
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver");
        
        estilizarBoton(btnGuardar, new Color(46, 204, 113));
        estilizarBoton(btnVolver, new Color(149, 165, 166));
        
        btnGuardar.addActionListener(e -> {
            game.cambiarDificultad(comboDif.getSelectedIndex() + 1);
            game.cambiarModo(comboModo.getSelectedIndex() + 1);
            JOptionPane.showMessageDialog(this, "Guardado!");
        });
        
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, MENU));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelPerfil() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JLabel lblTitulo = new JLabel("MI PERFIL", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(40, 0, 30, 0));
        
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(new Color(0, 0, 204));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lbl1 = new JLabel("Username:");
        lbl1.setForeground(Color.WHITE);
        lbl1.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblPerfilUser = new JLabel("");
        lblPerfilUser.setForeground(Color.CYAN);
        lblPerfilUser.setFont(new Font("Arial", Font.PLAIN, 18));
        
        JLabel lbl2 = new JLabel("Password:");
        lbl2.setForeground(Color.WHITE);
        lbl2.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblPerfilPass = new JLabel("********");
        lblPerfilPass.setForeground(Color.CYAN);
        lblPerfilPass.setFont(new Font("Arial", Font.PLAIN, 18));
        
        JLabel lbl3 = new JLabel("Puntos:");
        lbl3.setForeground(Color.WHITE);
        lbl3.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblPerfilPuntos = new JLabel("");
        lblPerfilPuntos.setForeground(Color.CYAN);
        lblPerfilPuntos.setFont(new Font("Arial", Font.PLAIN, 18));
        
        gbc.gridx = 0; gbc.gridy = 0;
        panelDatos.add(lbl1, gbc);
        gbc.gridx = 1;
        panelDatos.add(lblPerfilUser, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panelDatos.add(lbl2, gbc);
        gbc.gridx = 1;
        panelDatos.add(lblPerfilPass, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panelDatos.add(lbl3, gbc);
        gbc.gridx = 1;
        panelDatos.add(lblPerfilPuntos, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(0, 0, 204));
        
        JButton btnModificar = new JButton("Modificar");
        JButton btnVolver = new JButton("Volver");
        
        estilizarBoton(btnModificar, new Color(52, 152, 219));
        estilizarBoton(btnVolver, new Color(149, 165, 166));
        
        btnModificar.addActionListener(e -> {
            String newUser = JOptionPane.showInputDialog(this, "Nuevo username:", game.getCurrentUser().getUsername());
            if (newUser != null && !newUser.isEmpty()) {
                game.getCurrentUser().setUsername(newUser);
            }
            
            String newPass = JOptionPane.showInputDialog(this, "Nuevo password:");
            if (newPass != null && !newPass.isEmpty()) {
                game.getCurrentUser().setPassword(newPass);
            }
            
            actualizarPerfil();
            JOptionPane.showMessageDialog(this, "Actualizado!");
        });
        
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, MENU));
        
        panelBotones.add(btnModificar);
        panelBotones.add(btnVolver);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelDatos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 0, 204));
        
        JLabel lblTitulo = new JLabel("REPORTES", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JTabbedPane tabs = new JTabbedPane();
        
        JPanel panelLogs = new JPanel(new BorderLayout());
        panelLogs.setBackground(new Color(0, 0, 204));
        
        txtLogs = new JTextArea(15, 40);
        txtLogs.setEditable(false);
        txtLogs.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollLogs = new JScrollPane(txtLogs);
        
        panelLogs.add(scrollLogs, BorderLayout.CENTER);
        tabs.addTab("Mis Juegos", panelLogs);
        
        JPanel panelRanking = new JPanel(new BorderLayout());
        panelRanking.setBackground(new Color(0, 0, 204));
        
        txtRanking = new JTextArea(15, 40);
        txtRanking.setEditable(false);
        txtRanking.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollRanking = new JScrollPane(txtRanking);
        
        panelRanking.add(scrollRanking, BorderLayout.CENTER);
        tabs.addTab("Ranking", panelRanking);
        
        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.setBackground(new Color(0, 0, 204));
        
        JButton btnVolver = new JButton("Volver");
        estilizarBoton(btnVolver, new Color(149, 165, 166));
        btnVolver.addActionListener(e -> cardLayout.show(mainPanel, MENU));
        
        panelBoton.add(btnVolver);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(tabs, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void iniciarJuego() {
        usernameJugador2 = JOptionPane.showInputDialog(this, 
                          "Ingresa el username del Jugador 2:");
        
        if (usernameJugador2 == null || usernameJugador2.isEmpty()) return;
        
        playerJugador2 = game.buscarJugador(usernameJugador2);
        
        if (playerJugador2 == null) {
            JOptionPane.showMessageDialog(this, "Ese jugador no existe");
            return;
        }
        
        if (usernameJugador2.equals(game.getCurrentUser().getUsername())) {
            JOptionPane.showMessageDialog(this, "No puedes jugar contra ti mismo");
            return;
        }
        
        game.iniciarPartida(usernameJugador2);
        turnoj1 = true;
        reiniciarColocacion();
        cardLayout.show(mainPanel, COLOCAR_BARCOS);
    }
    
    private void reiniciarColocacion() {
        tableroTemp = new Tablero();
        barcoActualIndex = 0;
        esHorizontal = false;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                botonesColocar[i][j].setText("~");
                botonesColocar[i][j].setBackground(new Color(30, 144, 255));
            }
        }
        
        actualizarInstrucciones();
    }
    
    private void actualizarInstrucciones() {
        if (barcoActualIndex >= game.obtenerCantidadBarcos()) {
            return;
        }
        
        String barco = tiposBarcos[barcoActualIndex];
        String orientacion = esHorizontal ? "HORIZONTAL" : "VERTICAL";
        String nombreBarco = getNombreBarco(barco);
        
        lblInstrucciones.setText("Coloca: " + nombreBarco + " (" + barco + ") - " + orientacion);
    }
    
    private void colocarBarco(int fila, int col) {
        if (barcoActualIndex >= game.obtenerCantidadBarcos()) {
            JOptionPane.showMessageDialog(this, "Ya colocaste todos tus barcos!");
            return;
        }
        
        String codigoBarco = tiposBarcos[barcoActualIndex];
        Barco nuevoBarco = new Barco(codigoBarco);
        
        if (tableroTemp.agregarBarco(nuevoBarco, fila, col, esHorizontal)) {
            actualizarTableroVisual();
            barcoActualIndex++;
            
            if (barcoActualIndex >= game.obtenerCantidadBarcos()) {
                lblInstrucciones.setText("Todos los barcos colocados!");
                
                if (turnoj1) {
                    game.asignarTablero1(tableroTemp);
                    
                    JOptionPane.showMessageDialog(this, 
                        game.getCurrentUser().getUsername() + 
                        " ha colocado sus barcos.\nTurno de " + 
                        playerJugador2.getUsername());
                    
                    turnoj1 = false;
                    reiniciarColocacion();
                    
                } else {
                    game.asignarTablero2(tableroTemp);
                    
                    JOptionPane.showMessageDialog(this, 
                        playerJugador2.getUsername() + 
                        " ha colocado sus barcos!\nEl juego comenzara!");
                    
                    turnoJ1Juego = true;
                    actualizarInfoTurno();
                    actualizarTableroJuego();
                    cardLayout.show(mainPanel, JUEGO);
                }
            } else {
                actualizarInstrucciones();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se puede colocar ahi!");
        }
    }
    
    private void bombardear(int fila, int col) {
        String resultado;
        Tablero tableroEnemigo;
        Player jugadorActual;
        Player jugadorEnemigo;
        
        if (turnoJ1Juego) {
            jugadorActual = game.getCurrentUser();
            jugadorEnemigo = playerJugador2;
            tableroEnemigo = game.getTablero2();
            resultado = game.bombardearTablero2(fila, col);
        } else {
            jugadorActual = playerJugador2;
            jugadorEnemigo = game.getCurrentUser();
            tableroEnemigo = game.getTablero1();
            resultado = game.bombardearTablero1(fila, col);
        }
        
        actualizarTableroJuego();
        
        if (resultado.equals("FALLO")) {
            JOptionPane.showMessageDialog(this, "Agua! Fallaste");
            
        } else if (resultado.contains("HIT")) {
            String codigoBarco = resultado.split(":")[1];
            JOptionPane.showMessageDialog(this, 
                "Le diste a un " + getNombreBarcoCompleto(codigoBarco) + "!");
            
            tableroEnemigo.regenerarTablero();
            actualizarTableroJuego();
            
        } else if (resultado.contains("HUNDIDO")) {
            String codigoBarco = resultado.split(":")[1];
            JOptionPane.showMessageDialog(this, 
                "HUNDISTE el " + getNombreBarcoCompleto(codigoBarco) + "!");
            
            if ((turnoJ1Juego && game.jugador1Gano()) || 
                (!turnoJ1Juego && game.jugador2Gano())) {
                
                JOptionPane.showMessageDialog(this, 
                    jugadorActual.getUsername() + " HA GANADO!");
                
                game.registrarVictoria(jugadorActual, jugadorEnemigo);
                cardLayout.show(mainPanel, MENU);
                return;
            }
            
            tableroEnemigo.regenerarTablero();
            actualizarTableroJuego();
        }
        
        game.getTablero1().limpiarFallos();
        game.getTablero2().limpiarFallos();
        
        turnoJ1Juego = !turnoJ1Juego;
        actualizarInfoTurno();
        actualizarTableroJuego();
    }
    
    private void actualizarTableroJuego() {
        Tablero tableroMostrar;
        boolean mostrarBarcos = (game.getModo() == 1);
        
        if (turnoJ1Juego) {
            tableroMostrar = game.getTablero2();
        } else {
            tableroMostrar = game.getTablero1();
        }
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String celda = tableroMostrar.obtenerCelda(i, j);
                
                if (!mostrarBarcos && esCodigoBarco(celda)) {
                    botonesJuego[i][j].setText("~");
                    botonesJuego[i][j].setBackground(new Color(30, 144, 255));
                } else {
                    botonesJuego[i][j].setText(celda);
                    actualizarColorCelda(botonesJuego[i][j], celda);
                }
            }
        }
    }
    
    private void actualizarInfoTurno() {
        if (turnoJ1Juego) {
            lblTurnoJuego.setText("TURNO DE: " + game.getCurrentUser().getUsername());
            lblInfo.setText("Barcos enemigos restantes: " + game.getTablero2().contarBarcosVivos());
        } else {
            lblTurnoJuego.setText("TURNO DE: " + playerJugador2.getUsername());
            lblInfo.setText("Barcos enemigos restantes: " + game.getTablero1().contarBarcosVivos());
        }
    }
    
    private void actualizarColorCelda(JButton btn, String celda) {
        switch (celda) {
            case "X":
                btn.setBackground(Color.RED);
                break;
            case "F":
                btn.setBackground(Color.GRAY);
                break;
            case "~":
                btn.setBackground(new Color(30, 144, 255));
                break;
            default:
                btn.setBackground(new Color(46, 204, 113));
                break;
        }
    }
    
    private boolean esCodigoBarco(String celda) {
        return celda.equals("PA") || celda.equals("AZ") || 
               celda.equals("SM") || celda.equals("DT");
    }
    
    private void actualizarTableroVisual() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String celda = tableroTemp.obtenerCelda(i, j);
                botonesColocar[i][j].setText(celda);
                
                if (!celda.equals("~")) {
                    botonesColocar[i][j].setBackground(new Color(46, 204, 113));
                }
            }
        }
    }
    
    private void actualizarPerfil() {
        lblPerfilUser.setText(game.getCurrentUser().getUsername());
        lblPerfilPuntos.setText(String.valueOf(game.getCurrentUser().getPoints()));
    }
    
    private void actualizarReportes() {
        String[] logs = game.getCurrentUser().getLogs();
        StringBuilder sb = new StringBuilder("Ultimos 10 juegos:\n\n");
        for (String log : logs) {
            if (log != null) {
                sb.append(log).append("\n");
            }
        }
        txtLogs.setText(sb.toString());
        
        ArrayList<Player> ranking = game.obtenerRanking();
        StringBuilder rb = new StringBuilder("RANKING\n\n");
        rb.append(String.format("%-20s %s\n", "Usuario", "Puntos"));
        rb.append("--------------------------------\n");
        for (Player p : ranking) {
            rb.append(String.format("%-20s %d\n", p.getUsername(), p.getPoints()));
        }
        txtRanking.setText(rb.toString());
    }
    
    private String getNombreBarco(String codigo) {
        switch (codigo) {
            case "PA": return "Portaaviones (5 casillas)";
            case "AZ": return "Acorazado (4 casillas)";
            case "SM": return "Submarino (3 casillas)";
            case "DT": return "Destructor (2 casillas)";
            default: return "";
        }
    }
    
    private String getNombreBarcoCompleto(String codigo) {
        switch (codigo) {
            case "PA": return "Portaaviones";
            case "AZ": return "Acorazado";
            case "SM": return "Submarino";
            case "DT": return "Destructor";
            default: return "Barco";
        }
    }
   
    private void estilizarBoton(JButton btn, Color color) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(200, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void estilizarBotonMenu(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBackground(new Color(51, 255, 153));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BattleshipGUI());
    }
}
