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
    private JPanel panelMenuAVL;
    private JButton regresarAVL;
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
    private JPanel panelAgregarPersonaAVL;
    private JPanel panelActualizarPersonaAVL;
    private JPanel panelAVL;
    private JButton regresarButtonAVL;
    private JTextField personaBuscadaAVL;
    private JButton buscarButtonAVL;
    private JButton agregarPersonaButtonAVL;
    private JButton actualizarButtonAVL;
    private JButton eliminarButtonAVL;
    private JButton cargarArchivoButtonAVL;
    private JButton preorderButtonAVL;
    private JButton postorderButtonAVL;
    private JButton inorderButtonAVL;
    private JButton verArbolButtonAVL;
    private JTable tablaAVL;
    private JButton encriptarButtonAVL;
    private JButton desencriptarButtonAVL;
    private JButton descomprimirButtonAVL;
    private JButton comprimirButtonAVL;
    private JButton regresarAgregarButtonAVL;
    private JButton agregarNuevaPersonaButtonAVL;
    private JTextField dpiAVL;
    private JTextField nombreAVL;
    private JButton regresarActualizarButtonAVL;
    private JTextField dpiAntiguoAVL;
    private JTextField dpiNuevoAVL;
    private JTextField nombreNuevoAVL;
    private JTextField departamentoNuevoAVL;
    private JTextField municipioNuevoAVL;
    private JTextField dosisNuevaAVL;
    private JTextField primeraNuevaAVL;
    private JTextField segundaNuevaAVL;
    private JTextField terceraNuevaAVL;
    private JTextField lugarNuevoAVL;
    private JButton actualizarPersonaButtonAVL;

    // Modelo
    private File ArchivoSeleccionado;
    private ArbolBB arbolBinario;
    private ArbolAVL arbolAVL;

    private DefaultTableModel model;
    private DefaultTableModel modelAVL;

    private void irA(JPanel contenido) {
        panelPrincipal.removeAll();
        panelPrincipal.add(contenido);
        panelPrincipal.repaint();
        panelPrincipal.revalidate();
    }

    public App() {
        arbolBinario = new ArbolBB();
        arbolAVL = new ArbolAVL();
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
                    actualizarTablaABB();
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
                actualizarTablaABB();
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
                    actualizarTablaABB();
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
                    actualizarTablaABB();
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
                actualizarTablaABB();
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
                actualizarTablaABB();
            }
        });

        desencriptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolBinario.desencriptar();
                actualizarTablaABB();
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

        AVLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuAVL);
            }
        });

        regresarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuPrincipal);
            }
        });

        agregarPersonaButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelAgregarPersonaAVL);
            }
        });

        actualizarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelActualizarPersonaAVL);
            }
        });

        regresarAgregarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuAVL);
            }
        });

        regresarActualizarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irA(panelMenuAVL);
            }
        });

        cargarArchivoButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser ventanaEscogerArchivoAVL = new JFileChooser();
                String rutaEscritorio = System.getProperty("user.home") + File.separator + "OneDrive - Universidad Mariano Gálvez" + File.separator + "Escritorio";

                ventanaEscogerArchivoAVL.setCurrentDirectory(new File(rutaEscritorio));

                int escogido = ventanaEscogerArchivoAVL.showOpenDialog(null);

                if (escogido == JFileChooser.APPROVE_OPTION) {
                    ArchivoSeleccionado = ventanaEscogerArchivoAVL.getSelectedFile();

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

                            arbolAVL.insertarGUI(dpi, nombre);
                        }
                    }
                    actualizarTablaABB();
                } catch (FileNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });

        preorderButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.preorderArbol();
            }
        });

        buscarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpi = Long.parseLong(personaBuscadaAVL.getText());
                arbolAVL.buscar(dpi);
            }
        });

        verArbolButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Declarando el nombre de los archivos
                String archivoDot = "arbolAVL.dot";
                String nombreImagen = "arbolAVL.png";

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
                    ImageIcon imageIconAVL = new ImageIcon(nombreImagen);
                    JLabel imagenAVL = new JLabel(imageIconAVL);

                    JDialog dialogAVL = new JDialog();
                    dialogAVL.setTitle("ArbolBB");
                    dialogAVL.add(new JScrollPane(imagenAVL));
                    dialogAVL.pack();
                    dialogAVL.setLocationRelativeTo(null);
                    dialogAVL.setVisible(true);
                });
            }
        });

        postorderButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.postorder();
            }
        });

        inorderButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.inorder();
            }
        });

        agregarNuevaPersonaButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpi = Long.parseLong(dpiAVL.getText());
                String nombre = nombreAVL.getText();
                arbolAVL.insertarGUI(dpi, nombre);
                actualizarTablaAVL();
            }
        });

        actualizarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaAVL.getSelectedRow();
                if (filaSeleccionada != -1) {
                    irA(panelActualizarPersonaAVL);
                    String dpi = (String) tablaAVL.getValueAt(filaSeleccionada, 0);
                    String nombre = (String) tablaAVL.getValueAt(filaSeleccionada, 1);
                    String departamento = (String) tablaAVL.getValueAt(filaSeleccionada, 2);
                    String municipio = (String) tablaAVL.getValueAt(filaSeleccionada, 3);
                    String dosis = (String) tablaAVL.getValueAt(filaSeleccionada, 4);
                    String fechaPrimeraVacuna = (String) tablaAVL.getValueAt(filaSeleccionada, 5);
                    String fechaSegundaVacuna = (String) tablaAVL.getValueAt(filaSeleccionada, 6);
                    String fechaTerceraVacuna = (String) tablaAVL.getValueAt(filaSeleccionada, 7);
                    String lugarVacunacion = (String) tablaAVL.getValueAt(filaSeleccionada, 8);

                    dpiAntiguoAVL.setText(dpi);
                    dpiNuevoAVL.setText(dpi);
                    nombreNuevoAVL.setText(nombre);
                    departamentoNuevoAVL.setText(departamento);
                    municipioNuevoAVL.setText(municipio);
                    dosisNuevaAVL.setText(dosis);
                    primeraNuevaAVL.setText(fechaPrimeraVacuna);
                    segundaNuevaAVL.setText(fechaSegundaVacuna);
                    terceraNuevaAVL.setText(fechaTerceraVacuna);
                    lugarNuevoAVL.setText(lugarVacunacion);
                    actualizarTablaAVL();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        actualizarPersonaButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long dpiAntiguo = Long.parseLong(dpiAntiguoAVL.getText());
                long dpiNuevo = Long.parseLong(dpiNuevoAVL.getText());
                String nombre = nombreNuevoAVL.getText();
                String departamento = departamentoNuevoAVL.getText();
                String municipio = municipioNuevoAVL.getText();
                String dosis = dosisNuevaAVL.getText();
                String fechaPrimeraVacuna = primeraNuevaAVL.getText();
                String fechaSegundaVacuna = segundaNuevaAVL.getText();
                String fechaTerceraVacuna = terceraNuevaAVL.getText();
                String lugarVacunacion = lugarNuevoAVL.getText();

                arbolAVL.editar(dpiAntiguo, dpiNuevo, nombre, departamento, municipio, dosis, fechaPrimeraVacuna, fechaSegundaVacuna, fechaTerceraVacuna, lugarVacunacion);
                actualizarTablaAVL();
            }
        });

        eliminarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = tablaAVL.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String dpiBruto = (String) tablaAVL.getValueAt(filaSeleccionada, 0);
                    long dpi = Long.parseLong(dpiBruto);
                    arbolAVL.eliminar(dpi);
                    actualizarTablaAVL();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una fila!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        encriptarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.encriptar();
                actualizarTablaAVL();
            }
        });

        desencriptarButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.desencriptar();
                actualizarTablaAVL();
            }
        });

        comprimirButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.comprimir();
            }
        });

        descomprimirButtonAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbolAVL.descomprimir();
            }
        });
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
        crearTablaABB();
        crearTablaAVL();
    }

    void actualizarTablaABB() {
        model.setRowCount(0);
        cargarDatosTablaABB();
        model.fireTableDataChanged();
    }

    void crearTablaABB() {
        String[] columnas = {"DPI", "Nombre", "Departamento", "Municipio", "Cantidad de Dosis",
                "Primera vacuna", "Segunda vacuna", "Tercera vacuna", "Lugar de vacunacion"};
        model = new DefaultTableModel(columnas, 0);

        cargarDatosTablaABB();

        tablaNodosABB = new JTable(model);
    }

    void cargarDatosTablaABB() {
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

    void actualizarTablaAVL() {
        modelAVL.setRowCount(0);
        cargarDatosTablaAVL();
        modelAVL.fireTableDataChanged();
    }

    void crearTablaAVL() {
        String[] columnas = {"DPI", "Nombre", "Departamento", "Municipio", "Cantidad de Dosis",
                "Primera vacuna", "Segunda vacuna", "Tercera vacuna", "Lugar de vacunacion"};
        modelAVL = new DefaultTableModel(columnas, 0);

        cargarDatosTablaAVL();

        tablaAVL = new JTable(modelAVL);
    }

    void cargarDatosTablaAVL() {
        try (Scanner scanner = new Scanner(new File("arbolAVL.txt"))) {
            while (scanner.hasNext()) {
                String linea = scanner.nextLine();
                String[] datos = linea.split("\t");

                modelAVL.addRow(new Object[]{datos[1], datos[0], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8]});
            }
        } catch(IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
