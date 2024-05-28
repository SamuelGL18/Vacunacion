import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JPanel panelPrincipal;
    private JPanel panelNavegacion;
    private JButton mostrarABBMenu;
    private JButton mostrarAVLMenu;
    private JPanel panelContenido;
    private JPanel panelABB;
    private JPanel panelAVL;
    private JButton cargarArchivoButton;
    private JButton cargarArchivoButton1;

    public App() {
        mostrarABBMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelContenido.removeAll();
                panelContenido.add(panelABB);
                panelContenido.repaint();
                panelContenido.revalidate();
            }
        });
        mostrarAVLMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelContenido.removeAll();
                panelContenido.add(panelAVL);
                panelContenido.repaint();
                panelContenido.revalidate();
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
