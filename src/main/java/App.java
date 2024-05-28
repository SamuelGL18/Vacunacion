import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public App() {
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
