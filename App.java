import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JTextField txtEquipo;
    private JTextField txtUniversidad;
    private JTextField txtLenguaje;
    private JTextField txtNombre;
    private JTextField txtApellidos;

    private JPasswordField txtPassword;
    private JPasswordField txtConfirmacion;

    private JTextArea areaResultado;

    private EquipoMaratonProgramacion equipo;

    public App() {

        setTitle("Validación de Campos");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridLayout(0, 2, 5, 5));

        panelPrincipal.add(new JLabel("Nombre del equipo:"));
        txtEquipo = new JTextField();
        panelPrincipal.add(txtEquipo);

        panelPrincipal.add(new JLabel("Universidad:"));
        txtUniversidad = new JTextField();
        panelPrincipal.add(txtUniversidad);

        panelPrincipal.add(new JLabel("Lenguaje de programación:"));
        txtLenguaje = new JTextField();
        panelPrincipal.add(txtLenguaje);

        JButton btnCrearEquipo = new JButton("Crear Equipo");
        panelPrincipal.add(btnCrearEquipo);

        panelPrincipal.add(new JLabel(""));

        panelPrincipal.add(new JLabel("Nombre integrante:"));
        txtNombre = new JTextField();
        panelPrincipal.add(txtNombre);

        panelPrincipal.add(new JLabel("Apellidos integrante:"));
        txtApellidos = new JTextField();
        panelPrincipal.add(txtApellidos);

        JButton btnAgregar = new JButton("Agregar Integrante");
        panelPrincipal.add(btnAgregar);

        panelPrincipal.add(new JLabel("Máximo 3 integrantes"));

        panelPrincipal.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelPrincipal.add(txtPassword);

        panelPrincipal.add(new JLabel("Confirmar contraseña:"));
        txtConfirmacion = new JPasswordField();
        panelPrincipal.add(txtConfirmacion);

        JButton btnValidar = new JButton("Validar Contraseña");
        panelPrincipal.add(btnValidar);

        panelPrincipal.add(new JLabel(""));

        add(panelPrincipal, BorderLayout.NORTH);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnCrearEquipo.addActionListener(e -> crearEquipo());
        btnAgregar.addActionListener(e -> agregarProgramador());
        btnValidar.addActionListener(e -> validarPassword());
    }

    private void crearEquipo() {

        equipo = new EquipoMaratonProgramacion(
                txtEquipo.getText(),
                txtUniversidad.getText(),
                txtLenguaje.getText());

        areaResultado.setText("Equipo creado correctamente.\n");
    }

    private void agregarProgramador() {

        try {

            if (equipo == null) {
                throw new Exception("Primero debe crear el equipo.");
            }

            EquipoMaratonProgramacion.validarCampo(
                    txtNombre.getText());

            EquipoMaratonProgramacion.validarCampo(
                    txtApellidos.getText());

            Programador programador =
                    new Programador(
                            txtNombre.getText(),
                            txtApellidos.getText());

            equipo.anadir(programador);

            areaResultado.setText(
                    "Integrante agregado correctamente.\n\n" +
                    equipo);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarPassword() {

        try {

            String password =
                    new String(txtPassword.getPassword());

            String confirmacion =
                    new String(txtConfirmacion.getPassword());

            if (password.length() < 8) {
                throw new Exception(
                        "La contraseña debe tener mínimo 8 caracteres.");
            }

            if (password.contains(" ")) {
                throw new Exception(
                        "La contraseña no puede tener espacios.");
            }

            boolean mayuscula = false;
            boolean letra = false;
            boolean numero = false;
            boolean especial = false;

            for (char c : password.toCharArray()) {

                if (Character.isUpperCase(c))
                    mayuscula = true;

                if (Character.isLetter(c))
                    letra = true;

                if (Character.isDigit(c))
                    numero = true;

                if (!Character.isLetterOrDigit(c))
                    especial = true;
            }

            if (!mayuscula)
                throw new Exception(
                        "Debe contener una mayúscula.");

            if (!letra)
                throw new Exception(
                        "Debe contener una letra.");

            if (!numero)
                throw new Exception(
                        "Debe contener un número.");

            if (!especial)
                throw new Exception(
                        "Debe contener un carácter especial.");

            if (!password.equals(confirmacion))
                throw new Exception(
                        "Las contraseñas no coinciden.");

            areaResultado.setText(
                    "Contraseña válida correctamente.");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new App().setVisible(true);
        });
    }
}

class EquipoMaratonProgramacion {

    private String nombreEquipo;
    private String universidad;
    private String lenguajeProgramacion;

    private Programador[] programadores;
    private int tamanoEquipo;

    public EquipoMaratonProgramacion(
            String nombreEquipo,
            String universidad,
            String lenguajeProgramacion) {

        this.nombreEquipo = nombreEquipo;
        this.universidad = universidad;
        this.lenguajeProgramacion = lenguajeProgramacion;

        programadores = new Programador[3];
        tamanoEquipo = 0;
    }

    public boolean estaLleno() {
        return tamanoEquipo == programadores.length;
    }

    public void anadir(Programador programador)
            throws Exception {

        if (estaLleno()) {
            throw new Exception(
                    "El equipo está completo. No se pudo agregar programador.");
        }

        programadores[tamanoEquipo] = programador;
        tamanoEquipo++;
    }

    public static void validarCampo(String campo)
            throws Exception {

        for (int i = 0; i < campo.length(); i++) {

            char c = campo.charAt(i);

            if (Character.isDigit(c)) {
                throw new Exception(
                        "El nombre no puede tener dígitos.");
            }
        }

        if (campo.length() >= 20) {
            throw new Exception(
                    "La longitud no debe ser superior a 20 caracteres.");
        }
    }

    @Override
    public String toString() {

        return "Equipo: " + nombreEquipo +
                "\nUniversidad: " + universidad +
                "\nLenguaje: " + lenguajeProgramacion +
                "\nIntegrantes registrados: " + tamanoEquipo;
    }
}

class Programador {

    private String nombre;
    private String apellidos;

    public Programador(
            String nombre,
            String apellidos) {

        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}