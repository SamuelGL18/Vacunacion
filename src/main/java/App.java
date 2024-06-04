import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private JPanel panelPrincipal;
    private JButton ABBButton;
    private JButton AVLButton;
    private JPanel panelMenuPrincipal;
    private JPanel panelABB;
    private JButton regresarABB;
    private JButton cargarArchivoABB;
    private JButton encriptarButton;
    private JPanel panelAVL;
    private JButton regresarAVL;
    private JButton cargarArchivoButton;
    private JButton button5;
    private JTextField nodoBuscadoABB;
    private JButton buscarButtonABB;
    private JButton agregarABBButton;
    private JButton comprimirABBButton;
    private JTable tablaNodosABB;
    private JButton recorridoPreordenButton;
    private JButton agregarPersonaButtonABB;
    private JPanel panelMenuABB;
    private JPanel panelAgregarPersonaABB;
    private JButton RegresarPanelABB;
    private JTextField nombreAgregarABB;
    private JButton agregarButtonABB;
    private JTextField dpiAgregarABB;
    private JButton verArbolButtonABB;
    private JButton eliminarABBButton;
    private JButton actualizarABBButton;
    private JPanel panelActualizarPersonaABB;
    private JButton regresarActualizarABBButton;
    private JTextField DPINuevoABB;
    private JTextField nombreCambiadoABB;
    private JButton actualizarPersonaABB;
    private JTextField DPIAntiguoABB;
    private JButton postorderButtonABB;
    private JButton inorderButtonABB;
    private JButton desencriptarButton;
    private JTextField departamentoNuevoABB;
    private JTextField municipioNuevoABB;
    private JTextField dosisNuevaABB;
    private JTextField lugarVacunacionNuevoABB;
    private JTextField fechaTerceraNuevaABB;
    private JTextField fechaSegundaNuevaABB;
    private JTextField fechaPrimeraNuevaABB;
    private JTextField dpiAntiguoABB;
    private JButton descomprimirABBButton;

    // Modelo
    private File ArchivoSeleccionado;
    private ArbolBB arbolBinario;

    private DefaultTableModel model;

    private void irA(JPanel contenido) {
        panelPrincipal.removeAll();
        panelPrincipal.add(contenido);
        panelPrincipal.repaint();
        panelPrincipal.revalidate();
    }

    public App() {
        arbolBinario = new ArbolBB();
        ABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelABB);
            }
        });
        regresarABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuPrincipal);
            }
        });
        regresarAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuPrincipal);
            }
        });
        AVLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelAVL);
            }
        });
        cargarArchivoABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ventanaEscogerArchivo = new JFileChooser();
                String rutaEscritorio = System.getProperty("user.home") + File.separator + "OneDrive - Universidad Mariano Gálvez" + File.separator + "Escritorio";

                ventanaEscogerArchivo.setCurrentDirectory(new File(rutaEscritorio));

                int escogido = ventanaEscogerArchivo.showOpenDialog(null);

                if (escogido == JFileChooser.APPROVE_OPTION) {
                    ArchivoSeleccionado = ventanaEscogerArchivo.getSelectedFile();

                    System.out.println("Archivo seleccionado: " + ArchivoSeleccionado.getAbsolutePath());
                }

                try(Scanner scanner = new Scanner(ArchivoSeleccionado)) {
                    while(scanner.hasNext()) {
                        String linea = scanner.nextLine();
                        String[] datos = linea.split("\t");

                        // Procesando datos
                        if (datos.length == 2) {
                            String nombre = datos[0];
                            String dpiPuro = datos[1].replaceAll("[^\\d]", "");
                            long dpi = Long.parseLong(dpiPuro);

                            arbolBinario.insertarGUI(dpi, nombre);
                        }
                    }
                    actualizarTabla();
                } catch (FileNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });


        recorridoPreordenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.preorderArbol();
            }
        });

        agregarPersonaButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelAgregarPersonaABB);
            }
        });
        RegresarPanelABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelABB);
            }
        });
        agregarButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpi = Long.parseLong(dpiAgregarABB.getText());
                String nombre = nombreAgregarABB.getText();
                arbolBinario.insertarGUI(dpi, nombre);
                actualizarTabla();
            }
        });
        verArbolButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Declarando el nombre de los archivos
                String archivoDot = "arbolBB.dot";
                String nombreImagen = "arbolBB.png";

                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", archivoDot, "-o", nombreImagen);
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();
                    int codigoSalida = process.waitFor();

                    if (codigoSalida == 0) {
                        System.out.println("Imagen creada!");
                    } else {
                        System.err.println("Hubo un error, error: " + codigoSalida);
                    }

                } catch (IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }

                // Agregar imagen a dialogo
                SwingUtilities.invokeLater(() -> {
                    ImageIcon imageIcon = new ImageIcon(nombreImagen);
                    JLabel imagen = new JLabel(imageIcon);

                    JDialog dialog = new JDialog();
                    dialog.setTitle("ArbolBB");
                    dialog.add(new JScrollPane(imagen));
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                });
            }
        });

        eliminarABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaNodosABB.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String dpiBruto = (String) tablaNodosABB.getValueAt(filaSeleccionada, 0);
                    long dpi = Long.parseLong(dpiBruto);
                    arbolBinario.eliminar(dpi);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        actualizarABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaNodosABB.getSelectedRow();
                if (filaSeleccionada != -1) {
                    irA(panelActualizarPersonaABB);
                    String dpi = (String) tablaNodosABB.getValueAt(filaSeleccionada, 0);
                    String nombre = (String) tablaNodosABB.getValueAt(filaSeleccionada, 1);
                    String departamento = (String) tablaNodosABB.getValueAt(filaSeleccionada, 2);
                    String municipio = (String) tablaNodosABB.getValueAt(filaSeleccionada, 3);
                    String dosis = (String) tablaNodosABB.getValueAt(filaSeleccionada, 4);
                    String fechaPrimeraVacuna = (String) tablaNodosABB.getValueAt(filaSeleccionada, 5);
                    String fechaSegundaVacuna = (String) tablaNodosABB.getValueAt(filaSeleccionada, 6);
                    String fechaTerceraVacuna = (String) tablaNodosABB.getValueAt(filaSeleccionada, 7);
                    String lugarVacunacion = (String) tablaNodosABB.getValueAt(filaSeleccionada, 8);

                    DPINuevoABB.setText(dpi);
                    dpiAntiguoABB.setText(dpi);
                    nombreCambiadoABB.setText(nombre);
                    departamentoNuevoABB.setText(departamento);
                    municipioNuevoABB.setText(municipio);
                    dosisNuevaABB.setText(dosis);
                    fechaPrimeraNuevaABB.setText(fechaPrimeraVacuna);
                    fechaSegundaNuevaABB.setText(fechaSegundaVacuna);
                    fechaTerceraNuevaABB.setText(fechaTerceraVacuna);
                    lugarVacunacionNuevoABB.setText(lugarVacunacion);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarActualizarABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelABB);
            }
        });
        actualizarPersonaABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpiAntiguo = Long.parseLong(dpiAntiguoABB.getText());
                long dpiNuevo = Long.parseLong(DPINuevoABB.getText());
                String nombre = nombreCambiadoABB.getText();
                String departamento = departamentoNuevoABB.getText();
                String municipio = municipioNuevoABB.getText();
                String dosis = dosisNuevaABB.getText();
                String fechaPrimeraVacuna = fechaPrimeraNuevaABB.getText();
                String fechaSegundaVacuna = fechaSegundaNuevaABB.getText();
                String fechaTerceraVacuna = fechaTerceraNuevaABB.getText();
                String lugarVacunacion = lugarVacunacionNuevoABB.getText();

                arbolBinario.editar(dpiAntiguo, dpiNuevo, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
                actualizarTabla();
            }
        });
        buscarButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpi = Long.parseLong(nodoBuscadoABB.getText());
                arbolBinario.buscar(dpi);
            }
        });
        postorderButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.postorder();
            }
        });
        inorderButtonABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.inorder();
            }
        });
        encriptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.encriptar();
                actualizarTabla();
            }
        });
        desencriptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.desencriptar();
                actualizarTabla();
            }
        });
        comprimirABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.comprimir();
            }
        });
        descomprimirABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.descomprimir();
            }
        });
    }

    void actualizarTabla() {
        model.setRowCount(0);
        cargarDatosTabla();
        model.fireTableDataChanged();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registro de Vacunacion");
        frame.setMinimumSize(new Dimension(450, 474));
        frame.setContentPane(new App().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        crearTabla();
    }

    void crearTabla() {
        String[] columnas = {"DPI", "Nombre", "Departamento", "Municipio", "Cantidad de Dosis",
                "Primera vacuna", "Segunda vacuna", "Tercera vacuna", "Lugar de vacunacion"};
        model = new DefaultTableModel(columnas, 0);

        cargarDatosTabla();

        tablaNodosABB = new JTable(model);
    }

    void cargarDatosTabla() {
        try (Scanner scanner = new Scanner(new File("arbolBB.txt"))) {
            while (scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split("\t");

                model.addRow(new Object[]{datos[1], datos[0], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8]});
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
