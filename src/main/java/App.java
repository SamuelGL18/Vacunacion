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
    private JTextField textField1;
    private JButton buscarButton;
    private JButton agregarABBButton;
    private JButton comprimirButton;
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
                // 1. Execute dot Command
                String dotFilePath = "arbolBB.dot"; // Replace with your DOT file path
                String outputImagePath = "output.png"; // Desired output image path

                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", dotFilePath, "-o", outputImagePath);
                    processBuilder.redirectErrorStream(true); // Merge error output with standard output
                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();

                    if (exitCode == 0) {
                        System.out.println("Image generation successful!");
                    } else {
                        System.err.println("Image generation failed. Exit code: " + exitCode);
                        // Optionally, read and display error output from the process
                    }

                } catch (IOException | InterruptedException exception) {
                    exception.printStackTrace();
                }

                // 2. Load and Display Image in JDialog
                SwingUtilities.invokeLater(() -> {
                    ImageIcon imageIcon = new ImageIcon(outputImagePath);
                    JLabel imageLabel = new JLabel(imageIcon);

                    JDialog dialog = new JDialog();
                    dialog.setTitle("Graph Visualization");
                    dialog.add(new JScrollPane(imageLabel)); // Add scrollbars for large images
                    dialog.pack(); // Size the dialog to fit the image
                    dialog.setLocationRelativeTo(null); // Center the dialog on the screen
                    dialog.setVisible(true);
                });
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
        String[] columnas = {"DPI", "Nombre"};
        model = new DefaultTableModel(columnas, 0); // Start with empty model

        cargarDatosTabla();

        tablaNodosABB = new JTable(model);

        // Agregar listener
        tablaNodosABB.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaNodosABB.getSelectedRow();
                if (filaSeleccionada != -1) { // Check if a valid row is selected
                    String dpi = (String) tablaNodosABB.getValueAt(filaSeleccionada, 0); // Get DPI directly
                    String nombre = (String) tablaNodosABB.getValueAt(filaSeleccionada, 1); // Get Nombre directly
                    System.out.println("Nombre: " + nombre + " | DPI: " + dpi);
                }
            }
        });
    }

    void cargarDatosTabla() {
        try (Scanner scanner = new Scanner(new File("arbolBB.txt"))) {
            while (scanner.hasNext()) { // Use hasNextLine() for lines
                String linea = scanner.nextLine();
                String[] datos = linea.split("\t"); // Split by tabs

                if (datos.length == 2) {
                    // Create a row for the table model (swap order for DPI first)
                    model.addRow(new Object[]{datos[1], datos[0]});
                }
            }

        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
