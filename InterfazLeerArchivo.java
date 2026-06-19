package Archivos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * Programa de lectura de archivos de texto (Ejercicio 6.8).
 *
 * Incluye en un solo archivo:
 *  - Clase LeerArchivo: lee un archivo de texto utilizando un flujo de
 *    bytes (FileInputStream -> InputStreamReader -> BufferedReader).
 *  - Clase InterfazLeerArchivo: interfaz gráfica de usuario que permite
 *    seleccionar el archivo (ejercicio propuesto 1) y mostrar su
 *    contenido, opcionalmente convertido a mayúsculas (ejercicio
 *    propuesto 2).
 *
 * @version 1.0/2026
 */
public class InterfazLeerArchivo extends JFrame implements ActionListener {

    private JTextField campoRuta;
    private JButton botonExaminar;
    private JButton botonLeer;
    private JButton botonLimpiar;
    private JCheckBox checkMayusculas;
    private JTextArea areaContenido;

    private LeerArchivo lector;

    /**
     * Constructor de la clase InterfazLeerArchivo
     */
    public InterfazLeerArchivo() {
        lector = new LeerArchivo();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Lectura de Archivos de Texto - Universidad Nacional de Colombia");
        setSize(560, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Color azulUNAL = new Color(0, 48, 135);
        Color fondo = new Color(245, 247, 250);

        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout(10, 10));
        contenedor.setBackground(fondo);

        // Panel título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(azulUNAL);
        JLabel titulo = new JLabel("Lectura de Archivo de Texto");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelTitulo.add(titulo);
        contenedor.add(panelTitulo, BorderLayout.NORTH);

        // Panel superior: selección de archivo
        JPanel panelSuperior = new JPanel(new BorderLayout(8, 8));
        panelSuperior.setBackground(fondo);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        campoRuta = new JTextField();
        campoRuta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campoRuta.setEditable(false);
        campoRuta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 190, 210)),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        botonExaminar = crearBoton("Examinar...", azulUNAL);
        botonExaminar.addActionListener(this);

        panelSuperior.add(campoRuta, BorderLayout.CENTER);
        panelSuperior.add(botonExaminar, BorderLayout.EAST);

        // Fila de opciones (checkbox)
        JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelOpciones.setBackground(fondo);

        checkMayusculas = new JCheckBox("Mostrar contenido en MAYÚSCULAS");
        checkMayusculas.setBackground(fondo);
        checkMayusculas.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panelOpciones.add(checkMayusculas);

        JPanel panelNorteCompleto = new JPanel(new BorderLayout());
        panelNorteCompleto.setBackground(fondo);
        panelNorteCompleto.add(panelSuperior, BorderLayout.NORTH);
        panelNorteCompleto.add(panelOpciones, BorderLayout.SOUTH);

        contenedor.add(panelNorteCompleto, BorderLayout.NORTH);

        // Panel central: área de texto con scroll
        areaContenido = new JTextArea();
        areaContenido.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaContenido.setEditable(false);
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);
        areaContenido.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(areaContenido);
        scroll.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(0, 20, 10, 20),
            BorderFactory.createLineBorder(new Color(180, 190, 210))));

        contenedor.add(scroll, BorderLayout.CENTER);

        // Panel inferior: botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(fondo);

        botonLeer = crearBoton("Leer archivo", azulUNAL);
        botonLeer.addActionListener(this);

        botonLimpiar = crearBoton("Limpiar", new Color(180, 30, 30));
        botonLimpiar.addActionListener(this);

        panelBotones.add(botonLeer);
        panelBotones.add(botonLimpiar);

        contenedor.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object fuente = e.getSource();

        if (fuente == botonExaminar) {
            // Ejercicio propuesto 1: seleccionar el archivo en lugar de tenerlo fijo
            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccionar archivo de texto");
            selector.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Archivos de texto (*.txt)", "txt"));

            int resultado = selector.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                campoRuta.setText(selector.getSelectedFile().getAbsolutePath());
            }

        } else if (fuente == botonLeer) {
            String ruta = campoRuta.getText().trim();
            if (ruta.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un archivo de texto primero.",
                    "Archivo no seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String contenido;
                if (checkMayusculas.isSelected()) {
                    // Ejercicio propuesto 2: contenido convertido a mayúsculas
                    contenido = lector.leerContenidoMayusculas(ruta);
                } else {
                    contenido = lector.leerContenido(ruta);
                }
                areaContenido.setText(contenido);
                areaContenido.setCaretPosition(0);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "No se pudo leer el archivo.",
                    "Error de lectura", JOptionPane.ERROR_MESSAGE);
            }

        } else if (fuente == botonLimpiar) {
            campoRuta.setText("");
            areaContenido.setText("");
            checkMayusculas.setSelected(false);
        }
    }

    /**
     * Método principal que lanza la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazLeerArchivo());
    }
}

/**
 * Esta clase denominada LeerArchivo tiene como objetivo leer los datos
 * presentes en un archivo de texto con extensión .txt, ya sea con su
 * formato original o convertido completamente a mayúsculas.
 * @version 1.2/2020
 */
class LeerArchivo {

    /**
     * Método que lee un archivo de texto y retorna su contenido completo
     * como una cadena de caracteres, conservando los saltos de línea.
     * @param nombreArchivo Ruta del archivo de texto a leer
     * @return El contenido completo del archivo
     * @throws IOException Excepción que indica que no se pudo leer el archivo
     */
    public String leerContenido(String nombreArchivo) throws IOException {
        FileInputStream archivo;       // Definición de flujo de datos
        InputStreamReader conversor;   // Definición del flujo de lectura
        BufferedReader filtro;         // Definición del buffer
        String linea;
        StringBuilder contenido = new StringBuilder();

        /* Crea los objetos FileInputStream, InputStreamReader y BufferedReader */
        archivo = new FileInputStream(nombreArchivo);
        conversor = new InputStreamReader(archivo);
        filtro = new BufferedReader(conversor);

        linea = filtro.readLine();
        while (linea != null) {                  // Mientras existan líneas por leer
            contenido.append(linea).append("\n"); // Acumula la línea leída
            linea = filtro.readLine();            // Lee la siguiente línea
        }
        filtro.close(); // Cierra el archivo

        return contenido.toString();
    }

    /**
     * Método que lee un archivo de texto y retorna su contenido completo
     * convertido a mayúsculas (ejercicio propuesto 2).
     * @param nombreArchivo Ruta del archivo de texto a leer
     * @return El contenido del archivo en mayúsculas
     * @throws IOException Excepción que indica que no se pudo leer el archivo
     */
    public String leerContenidoMayusculas(String nombreArchivo) throws IOException {
        String contenido = leerContenido(nombreArchivo);
        return contenido.toUpperCase();
    }
}
