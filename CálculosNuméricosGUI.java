package Excepciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CálculosNuméricosGUI extends JFrame {

    public CálculosNuméricosGUI() {
        super("Ejercicio 6.6 - Cálculos numéricos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Log y raíz", crearPanelCalculosBasicos());
        tabs.addTab("Recta", crearPanelRecta());
        tabs.addTab("Ec. cuadrática", crearPanelEcuacionCuadratica());
        tabs.addTab("Base numérica", crearPanelConversionBase());

        add(tabs);
        setPreferredSize(new Dimension(420, 320));
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelCalculosBasicos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField campoValor = new JTextField();
        JTextArea areaResultado = crearAreaResultado();

        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        formulario.add(new JLabel("Valor:"));
        formulario.add(campoValor);

        JButton boton = new JButton("Calcular logaritmo y raíz");
        boton.addActionListener((ActionEvent evento) -> {
            try {
                double valor = Double.parseDouble(campoValor.getText().trim());
                double logaritmo = calcularLogaritmoNeperiano(valor);
                double raiz = calcularRaízCuadrada(valor);
                mostrarExito(areaResultado,
                    "Logaritmo neperiano = " + logaritmo + "\n" +
                    "Raíz cuadrada = " + raiz);
            } catch (NumberFormatException e) {
                mostrarError(areaResultado, "El valor debe ser numérico.");
            } catch (ArithmeticException e) {
                mostrarError(areaResultado, e.getMessage());
            }
        });
        formulario.add(new JLabel());
        formulario.add(boton);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelRecta() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField campoX1 = new JTextField();
        JTextField campoY1 = new JTextField();
        JTextField campoX2 = new JTextField();
        JTextField campoY2 = new JTextField();
        JTextArea areaResultado = crearAreaResultado();

        JPanel formulario = new JPanel(new GridLayout(5, 2, 8, 8));
        formulario.add(new JLabel("x1:"));
        formulario.add(campoX1);
        formulario.add(new JLabel("y1:"));
        formulario.add(campoY1);
        formulario.add(new JLabel("x2:"));
        formulario.add(campoX2);
        formulario.add(new JLabel("y2:"));
        formulario.add(campoY2);

        JButton boton = new JButton("Calcular pendiente y punto medio");
        boton.addActionListener((ActionEvent evento) -> {
            try {
                double x1 = Double.parseDouble(campoX1.getText().trim());
                double y1 = Double.parseDouble(campoY1.getText().trim());
                double x2 = Double.parseDouble(campoX2.getText().trim());
                double y2 = Double.parseDouble(campoY2.getText().trim());

                double pendiente = calcularPendiente(x1, y1, x2, y2);
                double[] medio = calcularPuntoMedio(x1, y1, x2, y2);

                mostrarExito(areaResultado,
                    "Pendiente = " + pendiente + "\n" +
                    "Punto medio = (" + medio[0] + ", " + medio[1] + ")");
            } catch (NumberFormatException e) {
                mostrarError(areaResultado, "Las coordenadas deben ser numéricas.");
            } catch (ArithmeticException e) {
                mostrarError(areaResultado, e.getMessage());
            }
        });
        formulario.add(new JLabel());
        formulario.add(boton);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelEcuacionCuadratica() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField campoA = new JTextField();
        JTextField campoB = new JTextField();
        JTextField campoC = new JTextField();
        JTextArea areaResultado = crearAreaResultado();

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        formulario.add(new JLabel("a:"));
        formulario.add(campoA);
        formulario.add(new JLabel("b:"));
        formulario.add(campoB);
        formulario.add(new JLabel("c:"));
        formulario.add(campoC);

        JButton boton = new JButton("Calcular raíces");
        boton.addActionListener((ActionEvent evento) -> {
            try {
                double a = Double.parseDouble(campoA.getText().trim());
                double b = Double.parseDouble(campoB.getText().trim());
                double c = Double.parseDouble(campoC.getText().trim());

                double[] raices = calcularRaícesEcuaciónCuadrática(a, b, c);
                mostrarExito(areaResultado,
                    "x1 = " + raices[0] + "\n" +
                    "x2 = " + raices[1]);
            } catch (NumberFormatException e) {
                mostrarError(areaResultado, "Los coeficientes deben ser numéricos.");
            } catch (ArithmeticException e) {
                mostrarError(areaResultado, e.getMessage());
            }
        });
        formulario.add(new JLabel());
        formulario.add(boton);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelConversionBase() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField campoNumero = new JTextField();
        JTextField campoBase = new JTextField();
        JTextArea areaResultado = crearAreaResultado();

        JPanel formulario = new JPanel(new GridLayout(3, 2, 8, 8));
        formulario.add(new JLabel("Número (base 10):"));
        formulario.add(campoNumero);
        formulario.add(new JLabel("Base destino:"));
        formulario.add(campoBase);

        JButton boton = new JButton("Convertir");
        boton.addActionListener((ActionEvent evento) -> {
            try {
                int numero = Integer.parseInt(campoNumero.getText().trim());
                int base = Integer.parseInt(campoBase.getText().trim());

                String resultado = convertirBaseB(numero, base);
                mostrarExito(areaResultado, numero + " en base " + base + " = " + resultado);
            } catch (NumberFormatException e) {
                mostrarError(areaResultado, "El número y la base deben ser enteros.");
            } catch (IllegalArgumentException e) {
                mostrarError(areaResultado, e.getMessage());
            }
        });
        formulario.add(new JLabel());
        formulario.add(boton);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
        return panel;
    }

    private static double calcularLogaritmoNeperiano(double valor) {
        if (valor < 0) {
            throw new ArithmeticException("El valor debe ser un número positivo para calcular el logaritmo.");
        }
        return Math.log(valor);
    }

    private static double calcularRaízCuadrada(double valor) {
        if (valor < 0) {
            throw new ArithmeticException("El valor debe ser un número positivo para calcular la raíz cuadrada.");
        }
        return Math.sqrt(valor);
    }

    private static double calcularPendiente(double x1, double y1, double x2, double y2) {
        if (x2 == x1) {
            throw new ArithmeticException("La pendiente no está definida cuando x1 es igual a x2.");
        }
        return (y2 - y1) / (x2 - x1);
    }

    private static double[] calcularPuntoMedio(double x1, double y1, double x2, double y2) {
        return new double[] { (x1 + x2) / 2, (y1 + y2) / 2 };
    }

    private static double[] calcularRaícesEcuaciónCuadrática(double a, double b, double c) {
        if (a == 0) {
            throw new ArithmeticException("El coeficiente a no puede ser cero en una ecuación cuadrática.");
        }
        double discriminante = b * b - 4 * a * c;
        if (discriminante < 0) {
            throw new ArithmeticException("La ecuación no tiene raíces reales.");
        }
        double raiz1 = (-b + Math.sqrt(discriminante)) / (2 * a);
        double raiz2 = (-b - Math.sqrt(discriminante)) / (2 * a);
        return new double[] { raiz1, raiz2 };
    }

    private static String convertirBaseB(int numero, int base) {
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("La base debe estar entre 2 y 36.");
        }
        if (numero < 0) {
            throw new IllegalArgumentException("El número debe ser mayor o igual a cero.");
        }
        return Integer.toString(numero, base).toUpperCase();
    }

    private static JTextArea crearAreaResultado() {
        JTextArea area = new JTextArea(6, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    private static JScrollPane envolverConTitulo(JTextArea area, String titulo) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createTitledBorder(titulo));
        return scroll;
    }

    private static void mostrarExito(JTextArea area, String texto) {
        area.setForeground(new Color(0, 100, 0));
        area.setText(texto);
    }

    private static void mostrarError(JTextArea area, String texto) {
        area.setForeground(Color.RED);
        area.setText(texto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CálculosNuméricosGUI().setVisible(true));
    }
}
