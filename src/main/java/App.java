import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
    private JButton agregarButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton comprimirButton;

    // Modelo
    private File ArchivoSeleccionado;
    private ArbolBB arbolBinario;


    public App() {
        arbolBinario = new ArbolBB();
        ABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPrincipal.removeAll();
                panelPrincipal.add(panelABB);
                panelPrincipal.repaint();
                panelPrincipal.revalidate();
            }
        });
        regresarABB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPrincipal.removeAll();
                panelPrincipal.add(panelMenuPrincipal);
                panelPrincipal.repaint();
                panelPrincipal.revalidate();
            }
        });
        regresarAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPrincipal.removeAll();
                panelPrincipal.add(panelMenuPrincipal);
                panelPrincipal.repaint();
                panelPrincipal.revalidate();
            }
        });
        AVLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPrincipal.removeAll();
                panelPrincipal.add(panelAVL);
                panelPrincipal.repaint();
                panelPrincipal.revalidate();
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

                            arbolBinario.insertar(dpi, nombre);

//                        System.out.println(
//                                "Nombre: " + nombre +
//                                " DPI: " + dpi
//                        );

                        } else {
                            System.out.println("El archivo no tiene datos estructurados");
                        }
                    }
                    arbolBinario.preorder();
                } catch (FileNotFoundException exception) {
                    System.out.println(exception.getMessage());
                }
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
}
