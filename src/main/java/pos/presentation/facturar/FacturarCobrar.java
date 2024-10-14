package pos.presentation.facturar;

import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Factura;
import pos.logic.Service;
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
    private View view;
    private Model model;

    public FacturarCobrar(pos.presentation.facturar.Controller facturarController,
                          View view, Model model) {
        this.facturarController = facturarController;
        this.view = view;
        this.model = model;

        // Setear el importe correcto de la factura actual
        double totalFactura = calcularTotalFactura();
        importeLabel.setText(String.valueOf(totalFactura));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Evento del botón COBRAR
        buttonOK.addActionListener(e -> {
            try {
                realizarPago();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos en los campos de pago.");
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // Manejo del cierre de la ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Teclas de escape para cancelar
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        // Validación para solo permitir números y punto decimal
        agregarValidacionNumerica(textFieldEfectivo);
        agregarValidacionNumerica(textFieldTarjeta);
        agregarValidacionNumerica(textFieldCheque);
        agregarValidacionNumerica(textFieldSinpe);
    }

    private double calcularTotalFactura()
    {
        // Sumar el total de los productos en la lista del modelo
        return model.getList().stream().mapToDouble(linea -> linea.getImporte()).sum();
    }

    private void agregarValidacionNumerica(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b' && c != '.') {
                    e.consume();
                }
            }
        });
    }

    private void realizarPago() {
        try {
            double totalFactura = calcularTotalFactura();  // Asegurar que estamos trabajando con el total correcto
            double pagoEfectivo = obtenerValorDeCampo(textFieldEfectivo);
            double pagoTarjeta = obtenerValorDeCampo(textFieldTarjeta);
            double pagoCheque = obtenerValorDeCampo(textFieldCheque);
            double pagoSinpe = obtenerValorDeCampo(textFieldSinpe);

            double totalPagado = pagoEfectivo + pagoTarjeta + pagoCheque + pagoSinpe;

            if (validarPago(totalPagado, totalFactura)) {
                // Crear la factura
                Factura factura = new Factura(generarNumeroFactura(), view.getSelectedCliente(), view.getSelectedCajero(), model.getList());

                Service.getInstance().create(factura);

                JOptionPane.showMessageDialog(null, "Pago realizado con éxito. Factura creada.");
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en los campos de pago. Asegúrate de que todos los valores sean numéricos.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generarNumeroFactura()
    {
        return "FAC-" + System.currentTimeMillis();
    }

    private boolean validarPago(double totalPagado, double totalFactura)
    {
        if (totalPagado >= totalFactura) {
            return true;
        } else {
            double falta = totalFactura - totalPagado;
            JOptionPane.showMessageDialog(null, "El monto pagado es insuficiente. Faltan: " + falta + " colones.");
            return false;
        }
    }

    private double obtenerValorDeCampo(JTextField textField) {
        String text = textField.getText().trim();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }

    private void onCancel() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que deseas cancelar el pago?",
                "Confirmar Cancelación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    public void setFacturarController(pos.presentation.facturar.Controller controller) {
        this.facturarController = controller;
    }

    public void setHistoricoController(pos.presentation.historico.Controller controller) {
        this.historicoController = controller;
    }
}