package pos.presentation.facturar;

import javax.swing.*;
import java.awt.event.*;

public class FacturarCantidad extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel pagosPanel;
    private JLabel cantLabel;
    private JTextField cantTxt;
    private pos.presentation.facturar.Controller controller;

    public FacturarCantidad() {
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



        cantTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.setCantidad(Integer.parseInt(cantTxt.getText()));

            }
        });
    }

    public void setController(pos.presentation.facturar.Controller controller) {
        this.controller = controller;
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FacturarCantidad dialog = new FacturarCantidad();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
