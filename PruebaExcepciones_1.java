import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PruebaExcepciones_1 extends JFrame {

    private JTabbedPane pestanas;


    private JTextArea areaEj1;
    private JButton btnEjecutar1;
    private JButton btnLimpiar1;


    private JTextArea areaEj2;
    private JButton btnEjecutar2;
    private JButton btnLimpiar2;


    private JTextArea areaEj3;
    private JButton btnEjecutar3;
    private JButton btnLimpiar3;

    public PruebaExcepciones_1() {
        setTitle("Prueba Excepciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panelPrincipal.setBackground(new Color(240, 240, 245));

        JLabel lblTitulo = new JLabel("Excepciones", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        pestanas = new JTabbedPane();
        pestanas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        pestanas.addTab("PruebaExcepciones", crearPanel1());
        pestanas.addTab("FueraLímite (Propuesto 1)", crearPanel2());
        pestanas.addTab("FormatoNúmero (Propuesto 2)", crearPanel3());

        panelPrincipal.add(pestanas, BorderLayout.CENTER);
        add(panelPrincipal);
    }


    private JPanel crearPanel1() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 250));

        JLabel desc = new JLabel("<html><b>Enunciado:</b> ¿Cuál es el resultado de la ejecución del método main de PruebaExcepciones?</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panel.add(desc, BorderLayout.NORTH);

        areaEj1 = crearAreaTexto();
        panel.add(new JScrollPane(areaEj1), BorderLayout.CENTER);

        btnEjecutar1 = crearBotonEjecutar();
        btnLimpiar1  = crearBotonLimpiar();
        btnEjecutar1.addActionListener(e -> ejecutarPrueba1());
        btnLimpiar1.addActionListener(e -> limpiar(areaEj1));

        panel.add(crearPanelBotones(btnEjecutar1, btnLimpiar1), BorderLayout.SOUTH);
        return panel;
    }


    private JPanel crearPanel2() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 250));

        JLabel desc = new JLabel("<html><b>Propuesto 1:</b> ¿Cuál es el resultado de ExcepciónFueraLímite? (charAt(14) sobre \"Programación\")</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panel.add(desc, BorderLayout.NORTH);

        areaEj2 = crearAreaTexto();
        panel.add(new JScrollPane(areaEj2), BorderLayout.CENTER);

        btnEjecutar2 = crearBotonEjecutar();
        btnLimpiar2  = crearBotonLimpiar();
        btnEjecutar2.addActionListener(e -> ejecutarPrueba2());
        btnLimpiar2.addActionListener(e -> limpiar(areaEj2));

        panel.add(crearPanelBotones(btnEjecutar2, btnLimpiar2), BorderLayout.SOUTH);
        return panel;
    }


    private JPanel crearPanel3() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(245, 245, 250));

        JLabel desc = new JLabel("<html><b>Propuesto 2:</b> ¿Cuál es el resultado de ExcepciónFormatoNúmero? (parseInt(\"Número\"))</html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panel.add(desc, BorderLayout.NORTH);

        areaEj3 = crearAreaTexto();
        panel.add(new JScrollPane(areaEj3), BorderLayout.CENTER);

        btnEjecutar3 = crearBotonEjecutar();
        btnLimpiar3  = crearBotonLimpiar();
        btnEjecutar3.addActionListener(e -> ejecutarPrueba3());
        btnLimpiar3.addActionListener(e -> limpiar(areaEj3));

        panel.add(crearPanelBotones(btnEjecutar3, btnLimpiar3), BorderLayout.SOUTH);
        return panel;
    }


    private void ejecutarPrueba1() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("Ingresando al primer try\n");
            int divisor = 0;
            double cociente = 10000 / divisor;
            sb.append("Después de la división\n");
        } catch (ArithmeticException e) {
            sb.append("División por cero\n");
        } finally {
            sb.append("Ingresando al primer finally\n");
        }
        try {
            sb.append("Ingresando al segundo try\n");
            Object objeto = new Object();
            objeto.toString();
            sb.append("Imprimiendo objeto\n");
        } catch (ArithmeticException e) {
            sb.append("División por cero\n");
        } catch (Exception e) {
            sb.append("Ocurrió una excepción\n");
        } finally {
            sb.append("Ingresando al segundo finally\n");
        }
        mostrar(areaEj1, sb.toString());
    }


    private void ejecutarPrueba2() {
        StringBuilder sb = new StringBuilder();
        try {
            String texto = "Programación";
            char caracter = texto.charAt(14);
            sb.append(caracter).append("\n");
        } catch (StringIndexOutOfBoundsException e) {
            sb.append("Indice de string por fuera del límite\n");
        }
        mostrar(areaEj2, sb.toString());
    }


    private void ejecutarPrueba3() {
        StringBuilder sb = new StringBuilder();
        try {
            int numero = Integer.parseInt("Número");
            sb.append(numero).append("\n");
        } catch (NumberFormatException e) {
            sb.append("Excepción de formato de número\n");
        } finally {
            sb.append("Ingresando al finally\n");
        }
        mostrar(areaEj3, sb.toString());
    }


    private JTextArea crearAreaTexto() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBackground(new Color(30, 30, 30));
        area.setForeground(new Color(120, 120, 120));
        area.setCaretColor(Color.WHITE);
        area.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        area.setText("Presione \"Ejecutar\" para ver el resultado...");
        return area;
    }

    private void mostrar(JTextArea area, String texto) {
        area.setForeground(new Color(180, 255, 180));
        area.setText(texto);
    }

    private void limpiar(JTextArea area) {
        area.setForeground(new Color(120, 120, 120));
        area.setText("Presione \"Ejecutar\" para ver el resultado...");
    }

    private JButton crearBotonEjecutar() {
        JButton btn = new JButton("Ejecutar");
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(new Color(40, 120, 200));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    private JButton crearBotonLimpiar() {
        JButton btn = new JButton("Limpiar");
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBackground(new Color(160, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    private JPanel crearPanelBotones(JButton ejecutar, JButton limpiar) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panel.setBackground(new Color(245, 245, 250));
        panel.add(ejecutar);
        panel.add(limpiar);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PruebaExcepciones_1().setVisible(true));
    }
}
