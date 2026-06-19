// removed package declaration so the file can be compiled/run from any location
// Original package: Excepciones


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VendedorGUI_2 extends JFrame {

    public VendedorGUI_2() {
        super("Excepciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Vendedor", new PanelVendedor());
        tabs.addTab("Cuenta bancaria", new PanelCuentaBancaria());
        tabs.addTab("Tabla ASCII", new PanelTablaASCII());

        add(tabs);
        setPreferredSize(new Dimension(480, 420));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendedorGUI_2().setVisible(true));
    }
}

class PanelVendedor extends JPanel {

    private JTextField campoNombre;
    private JTextField campoApellidos;
    private JTextField campoEdad;
    private JTextArea areaResultado;

    PanelVendedor() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        campoNombre = new JTextField();
        campoApellidos = new JTextField();
        campoEdad = new JTextField();

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Apellidos:"));
        formulario.add(campoApellidos);
        formulario.add(new JLabel("Edad:"));
        formulario.add(campoEdad);

        JButton boton = new JButton("Registrar vendedor");
        boton.addActionListener(this::registrar);
        formulario.add(new JLabel());
        formulario.add(boton);

        areaResultado = ComponentesGUI.crearAreaResultado();

        add(formulario, BorderLayout.NORTH);
        add(ComponentesGUI.envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
    }

    private void registrar(ActionEvent evento) {
        String nombre = campoNombre.getText().trim();
        String apellidos = campoApellidos.getText().trim();
        String textoEdad = campoEdad.getText().trim();

        try {
            int edad = Integer.parseInt(textoEdad);
            Vendedor vendedor = new Vendedor(nombre, apellidos);
            vendedor.verificarEdad(edad);
            ComponentesGUI.mostrarExito(areaResultado,
                "Nombre del vendedor = " + vendedor.getNombre() + "\n" +
                "Apellidos del vendedor = " + vendedor.getApellidos() + "\n" +
                "Edad del vendedor = " + edad);
        } catch (NumberFormatException e) {
            ComponentesGUI.mostrarError(areaResultado, "La edad debe ser un valor numérico.");
        } catch (IllegalArgumentException e) {
            ComponentesGUI.mostrarError(areaResultado, e.getMessage());
        }
    }
}

class PanelCuentaBancaria extends JPanel {

    private JTextField campoTitular;
    private JTextField campoSaldo;
    private JTextArea areaResultado;

    PanelCuentaBancaria() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(3, 2, 8, 8));
        campoTitular = new JTextField();
        campoSaldo = new JTextField();

        formulario.add(new JLabel("Titular:"));
        formulario.add(campoTitular);
        formulario.add(new JLabel("Saldo inicial:"));
        formulario.add(campoSaldo);

        JButton boton = new JButton("Crear cuenta");
        boton.addActionListener(this::crearCuenta);
        formulario.add(new JLabel());
        formulario.add(boton);

        areaResultado = ComponentesGUI.crearAreaResultado();

        add(formulario, BorderLayout.NORTH);
        add(ComponentesGUI.envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
    }

    private void crearCuenta(ActionEvent evento) {
        String titular = campoTitular.getText().trim();
        String textoSaldo = campoSaldo.getText().trim();

        try {
            double saldo = Double.parseDouble(textoSaldo);
            new CuentaBancaria(titular, saldo);
            ComponentesGUI.mostrarExito(areaResultado,
                "Titular = " + titular + "\n" +
                "Saldo = " + saldo);
        } catch (NumberFormatException e) {
            ComponentesGUI.mostrarError(areaResultado, "El saldo debe ser un valor numérico.");
        } catch (IllegalArgumentException e) {
            ComponentesGUI.mostrarError(areaResultado, e.getMessage());
        }
    }
}

class PanelTablaASCII extends JPanel {

    private TablaASCII tabla = new TablaASCII();
    private JTextField campoSimbolo;
    private JTextField campoNumero;
    private JTextArea areaResultado;

    PanelTablaASCII() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridLayout(2, 3, 8, 8));
        campoSimbolo = new JTextField();
        campoNumero = new JTextField();

        formulario.add(new JLabel("Símbolo:"));
        formulario.add(campoSimbolo);
        formulario.add(new JLabel());

        JButton botonSet = new JButton("Asociar (set)");
        botonSet.addActionListener(this::asociar);
        JButton botonGet = new JButton("Recuperar (get)");
        botonGet.addActionListener(this::recuperar);

        formulario.add(campoNumero);
        formulario.add(botonSet);
        formulario.add(botonGet);

        campoNumero.setToolTipText("Número a asociar (usado solo en 'Asociar')");

        areaResultado = ComponentesGUI.crearAreaResultado();

        add(formulario, BorderLayout.NORTH);
        add(ComponentesGUI.envolverConTitulo(areaResultado, "Resultado"), BorderLayout.CENTER);
    }

    private void asociar(ActionEvent evento) {
        String simbolo = campoSimbolo.getText();
        String textoNumero = campoNumero.getText().trim();

        try {
            int numero = Integer.parseInt(textoNumero);
            tabla.set(simbolo, numero);
            ComponentesGUI.mostrarExito(areaResultado, "Asociado: \"" + simbolo + "\" -> " + numero);
        } catch (NumberFormatException e) {
            ComponentesGUI.mostrarError(areaResultado, "El número debe ser un valor entero.");
        } catch (IllegalArgumentException e) {
            ComponentesGUI.mostrarError(areaResultado, e.getMessage());
        }
    }

    private void recuperar(ActionEvent evento) {
        String simbolo = campoSimbolo.getText();

        try {
            int valor = tabla.get(simbolo);
            ComponentesGUI.mostrarExito(areaResultado, "Valor de \"" + simbolo + "\" = " + valor);
        } catch (SimboloNoEncontradoException e) {
            ComponentesGUI.mostrarError(areaResultado, e.getMessage());
        } catch (IllegalArgumentException e) {
            ComponentesGUI.mostrarError(areaResultado, e.getMessage());
        }
    }
}

class ComponentesGUI {

    static JTextArea crearAreaResultado() {
        JTextArea area = new JTextArea(6, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    static JScrollPane envolverConTitulo(JTextArea area, String titulo) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createTitledBorder(titulo));
        return scroll;
    }

    static void mostrarExito(JTextArea area, String texto) {
        area.setForeground(new Color(0, 100, 0));
        area.setText(texto);
    }

    static void mostrarError(JTextArea area, String texto) {
        area.setForeground(Color.RED);
        area.setText(texto);
    }
}

class Vendedor {
    private final String nombre;
    private final String apellidos;

    Vendedor(String nombre, String apellidos) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del vendedor no puede estar vacío.");
        }
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new IllegalArgumentException("Los apellidos del vendedor no pueden estar vacíos.");
        }
        this.nombre = nombre.trim();
        this.apellidos = apellidos.trim();
    }

    void verificarEdad(int edad) {
        if (edad < 18 || edad > 99) {
            throw new IllegalArgumentException("La edad debe ser un número entre 18 y 99.");
        }
    }

    String getNombre() {
        return nombre;
    }

    String getApellidos() {
        return apellidos;
    }
}

class CuentaBancaria {
    private final String titular;
    private final double saldo;

    CuentaBancaria(String titular, double saldo) {
        if (titular == null || titular.trim().isEmpty()) {
            throw new IllegalArgumentException("El titular no puede estar vacío.");
        }
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo.");
        }
        this.titular = titular.trim();
        this.saldo = saldo;
    }

    String getTitular() {
        return titular;
    }

    double getSaldo() {
        return saldo;
    }
}

class TablaASCII {
    private final java.util.Map<String, Integer> mapa = new java.util.HashMap<>();

    void set(String simbolo, int numero) {
        if (simbolo == null || simbolo.isEmpty()) {
            throw new IllegalArgumentException("El símbolo no puede ser nulo o vacío.");
        }
        if (numero < 0 || numero > 255) {
            throw new IllegalArgumentException("El número debe estar entre 0 y 255.");
        }
        mapa.put(simbolo, numero);
    }

    int get(String simbolo) throws SimboloNoEncontradoException {
        if (simbolo == null || simbolo.isEmpty()) {
            throw new IllegalArgumentException("El símbolo no puede ser nulo o vacío.");
        }
        Integer valor = mapa.get(simbolo);
        if (valor == null) {
            throw new SimboloNoEncontradoException("Símbolo no encontrado: " + simbolo);
        }
        return valor;
    }
}

class SimboloNoEncontradoException extends Exception {
    SimboloNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
