package pos.presentation.facturar;

import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Factura;
import pos.presentation.historico.*;
import pos.presentation.facturar.*;
import javax.swing.*;
import java.awt.event.*;

public class FacturarCobrar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel pagosPanel;
    private JPanel importePanel;
    private JLabel importeLabel;
    private JTextField textFieldEfectivo;
    private JTextField textFieldTarjeta;
    private JTextField textFieldCheque;
    private JTextField textFieldSinpe;
    private JPanel ImportePanel;
    private JButton efectivoButton;
    private JButton tarjetaButton;
    private JButton chequeButton;
    private JButton sinpeButton;
    private JLabel MetodoDePago;
    private pos.presentation.facturar.Controller facturarController;
    private pos.presentation.historico.Controller historicoController;


    public FacturarCobrar(pos.presentation.facturar.Controller facturarController, pos.presentation.historico.Controller historicoController) {

        this.facturarController = facturarController;
        this.historicoController = historicoController;
        importeLabel.setText("0");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                historicoController.addFactura(new Factura());

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

        textFieldEfectivo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Only allow digits (0-9) and backspace (for deleting characters)
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();  // Ignore the event if it's not a digit or backspace
                }
            }
        });

        textFieldTarjeta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Only allow digits (0-9) and backspace (for deleting characters)
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();  // Ignore the event if it's not a digit or backspace
                }
            }
        });

        textFieldCheque.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Only allow digits (0-9) and backspace (for deleting characters)
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();  // Ignore the event if it's not a digit or backspace
                }
            }
        });

        textFieldSinpe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                // Only allow digits (0-9) and backspace (for deleting characters)
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();  // Ignore the event if it's not a digit or backspace
                }
            }
        });

        textFieldEfectivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                Integer pago = Integer.valueOf(textFieldEfectivo.getText()) + Integer.parseInt(importeLabel.getText());
                importeLabel.setText(pago.toString());

            }
        });

        textFieldTarjeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer pago = Integer.valueOf(textFieldTarjeta.getText()) + Integer.parseInt(importeLabel.getText());
                importeLabel.setText(pago.toString());

            }
        });
        textFieldCheque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer pago = Integer.valueOf(textFieldCheque.getText()) + Integer.parseInt(importeLabel.getText());
                importeLabel.setText(pago.toString());

            }
        });
        textFieldSinpe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer pago = Integer.valueOf(textFieldSinpe.getText()) + Integer.parseInt(importeLabel.getText());
                importeLabel.setText(pago.toString());
            }
        });
    }

    private void onOK() {
        onCobrar();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void setFacturarController(pos.presentation.facturar.Controller controller) {
        this.facturarController = controller;
    }

    public void setHistoricoController(pos.presentation.historico.Controller controller){

        this.historicoController = controller;
    }

    public static void main(String[] args) {
        FacturarCobrar dialog = new FacturarCobrar();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
