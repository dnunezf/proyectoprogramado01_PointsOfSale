package pos.presentation.facturar;

import pos.logic.Producto;
import pos.presentation.productos.Controller;
import javax.swing.*;
import java.awt.event.*;

public class FacturarBuscar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel busquedaPanel;
    private JLabel descLabel;
    private JTextField descTxt;
    private JPanel listadoPanel;
    private JScrollPane listadoScrollPanel;
    private JTable list;
    private pos.presentation.facturar.Controller controller;


    public FacturarBuscar() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        descTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try
                {
                    Producto filter = new Producto();

                    filter.setDescripcion(descTxt.getText());

                    controller.searchProducto(filter);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public void setController(pos.presentation.facturar.Controller controller) {
        this.controller = controller;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FacturarBuscar dialog = new FacturarBuscar();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
