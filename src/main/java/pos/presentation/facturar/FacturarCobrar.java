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
    private JLabel importeLabel;
    private JTextField textFieldEfectivo;
    private JTextField textFieldTarjeta;
    private JTextField textFieldCheque;
    private JTextField textFieldSinpe;

    private pos.presentation.facturar.Controller facturarController;
    private pos.presentation.historico.Controller historicoController;
    View view;
    Model model;

    public FacturarCobrar(pos.presentation.facturar.Controller facturarController,
                          pos.presentation.historico.Controller historicoController,
                          View view) {  // Asegúrate de recibir 'view'
        this.facturarController = facturarController;
        this.historicoController = historicoController;
        this.view = view;  // Inicializa 'view'
        importeLabel.setText("0");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Evento del botón COBRAR
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    realizarPago();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos en los campos de pago.");
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // Manejo del cierre de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Teclas de escape para cancelar
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Validación para solo permitir números
        agregarValidacionNumerica(textFieldEfectivo);
        agregarValidacionNumerica(textFieldTarjeta);
        agregarValidacionNumerica(textFieldCheque);
        agregarValidacionNumerica(textFieldSinpe);
    }

    private void agregarValidacionNumerica(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });
    }

    private void realizarPago() {
        double totalFactura = Double.parseDouble(importeLabel.getText());
        double pagoEfectivo = obtenerValorDeCampo(textFieldEfectivo);
        double pagoTarjeta = obtenerValorDeCampo(textFieldTarjeta);
        double pagoCheque = obtenerValorDeCampo(textFieldCheque);
        double pagoSinpe = obtenerValorDeCampo(textFieldSinpe);

        double totalPagado = pagoEfectivo + pagoTarjeta + pagoCheque + pagoSinpe;

        if (totalPagado >= totalFactura) {
            // Crear la factura y agregarla al histórico
            Factura factura = new Factura("01", view.getSelectedCliente(), view.getSelectedCajero(), model.getList());
            historicoController.addFactura(factura);
            JOptionPane.showMessageDialog(null, "Pago realizado con éxito. Factura creada.");
            dispose();
        } else {
            double falta = totalFactura - totalPagado;
            JOptionPane.showMessageDialog(null, "El monto pagado es insuficiente. Faltan: " + falta + " colones.");
        }
    }

    private double obtenerValorDeCampo(JTextField textField) {
        String text = textField.getText().trim();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }

    private void onCancel() {
        dispose();
    }

    public void setFacturarController(pos.presentation.facturar.Controller controller) {
        this.facturarController = controller;
    }

    public void setHistoricoController(pos.presentation.historico.Controller controller) {
        this.historicoController = controller;
    }
}
