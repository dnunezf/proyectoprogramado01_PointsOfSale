package pos.presentation.facturar;

import pos.Application;
import pos.logic.Cajero;
import pos.logic.Cliente;
import pos.logic.Linea;
import pos.logic.Producto;
import pos.presentation.productos.Controller;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class View implements PropertyChangeListener {
    private JButton cobrarButton;
    private JButton buscarButton;
    private JButton cantidadButton;
    private JButton quitarButton;
    private JButton descuentoButton;
    private JButton cancelarButton;
    private JScrollPane listadoScrollPanel;
    private JTable list;
    private JPanel panel;
    private JPanel datosClientePanel;
    private JLabel idLabel;
    private JComboBox<Cliente> comboBoxCli;
    private JPanel datosCajeros;
    private JPanel listadoFacturasPanel;
    private JPanel addProductPanel;
    private JLabel addProductLabel;
    private JTextField productCode;
    private JButton addProduct;
    private JComboBox<Cajero> comboBoxCaj;
    private JLabel cantidadArticulos;
    private JLabel subTotal;
    private JLabel descuentos;
    private JLabel total;


    /*M.V.C*/
    Model model;
    pos.presentation.facturar.Controller controller;
    pos.presentation.productos.Controller productosController;

    public void actualizarLabels(){

        cantidadArticulos.setText(Integer.toString(controller.getCantidadArticulos()));
        subTotal.setText(Float.toString(controller.getSubtotal()));
        descuentos.setText(Float.toString(controller.getDescuentos()));
        total.setText(Float.toString(controller.getTotal()));
    }

    public View() {
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturarCobrar cobrar = new FacturarCobrar();
                cobrar.setController(controller);
                cobrar.setSize(400, 400);
                cobrar.setLocationRelativeTo(null);
                cobrar.setVisible(true);

            }
        });




        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturarBuscar buscar = new FacturarBuscar();
                buscar.setController(productosController);
                buscar.setSize(400, 400);
                buscar.setLocationRelativeTo(null);
                buscar.setVisible(true);
            }
        });

        cantidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Cambios
                Icon descIcon = new ImageIcon(getClass().getResource("/pos/presentation/icons/list.png"));

                // Verifica si hay una línea seleccionada en la vista
                int selectedRow = list.getSelectedRow();

                if (selectedRow >= 0)
                {
                    // Mostrar un cuadro de diálogo para que el usuario ingrese la nueva cantidad
                    String input = JOptionPane.showInputDialog(getPanel(), "Ingrese la cantidad deseada:", "Actualizar Cantidad", JOptionPane.PLAIN_MESSAGE);

                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            // Convertir la entrada a un entero
                            int cantidad = Integer.parseInt(input.trim());

                            // Obtener la línea de producto actual
                            Linea lineaActual = model.getList().get(selectedRow);

                            // Actualizar la cantidad en la línea de producto
                            lineaActual.setCantidadVendida(cantidad);

                            // Actualizar el modelo
                            model.setList(model.getList());

                        } catch (NumberFormatException ex) {
                            // Manejar el caso en el que la entrada no es un número válido
                            JOptionPane.showMessageDialog(getPanel(), "Cantidad inválida. Por favor ingrese un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ninguna línea
                    JOptionPane.showMessageDialog(getPanel(), "Por favor, seleccione una línea de producto para actualizar la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                actualizarLabels();
            }
        });
        quitarButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                try {

                    int selectedRow = list.getSelectedRow();
                    Linea lineaActual = model.getList().get(selectedRow);

                    model.getList().remove(lineaActual);

                    model.setList(model.getList());

                    actualizarLabels();

                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(panel, "Por favor, seleccione una línea de producto para eliminarla.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        descuentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Cambios
                Icon descIcon = new ImageIcon(getClass().getResource("/pos/presentation/icons/discount.png"));

                // Verifica si hay una línea seleccionada en la vista
                int selectedRow = list.getSelectedRow();

                if (selectedRow >= 0)
                {
                    // Mostrar un cuadro de diálogo para que el usuario ingrese nuevo descento
                    String input = JOptionPane.showInputDialog(getPanel(), "Ingrese el descuento:", "Actualizar Descuento", JOptionPane.PLAIN_MESSAGE);

                    if (input != null && !input.trim().isEmpty()) {
                        try {
                            // Convertir la entrada a un float
                            float descuento = Float.parseFloat(input.trim());

                            // Obtener la línea de producto actual
                            Linea lineaActual = model.getList().get(selectedRow);

                            // Actualizar el descuento en la línea de producto
                            lineaActual.setDescuento(descuento);

                            // Actualizar el modelo
                            model.setList(model.getList());

                        } catch (NumberFormatException ex) {
                            // Manejar el caso en el que la entrada no es un número válido
                            JOptionPane.showMessageDialog(getPanel(), "Descuento inválido. Por favor ingrese un número correcto.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ninguna línea
                    JOptionPane.showMessageDialog(getPanel(), "Por favor, seleccione una línea de producto para actualizar el descuento.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                actualizarLabels();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.reset();

                model.setList(model.getList());

                actualizarLabels();
            }
        });

        // En View.java, maneja mejor las excepciones.
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Producto filter = new Producto();
                    filter.setCodigo(productCode.getText());
                    controller.add(filter); // Asegúrate de que 'controller' esté correctamente inicializado
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                actualizarLabels();
            }
        });

    }

    public void initialize(JTabbedPane tabbedPane, pos.presentation.cajeros.Model modelCajeros, pos.presentation.clientes.Model modelClientes) {
        // Configuración del panel y tab
        Icon facturaIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/bill.png"));
        tabbedPane.addTab("Facturar ", facturaIcon, this.getPanel());

        DefaultComboBoxModel<Cajero> comboBoxCajeros = new DefaultComboBoxModel<>();

        // Añade las categorías al modelo del JComboBox
        for (Cajero cajero : modelCajeros.getList()) {
            comboBoxCajeros.addElement(cajero);
        }

        DefaultComboBoxModel<Cliente> comboBoxClientes = new DefaultComboBoxModel<>();

        for (Cliente cliente : modelClientes.getList()) {
            comboBoxClientes.addElement(cliente);
        }

        // Asigna el modelo al JComboBox
        comboBoxCaj.setModel(comboBoxCajeros);
        comboBoxCli.setModel(comboBoxClientes);
    }

    public JPanel getPanel() {
        return panel;
    }


    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public pos.presentation.facturar.Controller getController() {
        return this.controller;
    }

    public void setMainController(pos.presentation.facturar.Controller controller) {
        this.controller = controller;
    }

    public void setProductosController(pos.presentation.productos.Controller controller) {

        this.productosController = controller;

    }

    public pos.presentation.productos.Controller getProductosController() {

        return this.productosController;

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST: {
                int[] cols = {TableModel.CODIGO, TableModel.ARTICULO, TableModel.CATEGORIA, TableModel.CANTIDAD, TableModel.PRECIO, TableModel.DESCUENTO, TableModel.NETO, TableModel.IMPORTE};
                if (model.getList() != null) {
                    TableModel tablaLineas = new TableModel(cols, model.getList());
                    list.setModel(tablaLineas);
                    list.setRowHeight(30);

                    TableColumnModel columnModel = list.getColumnModel();
                    columnModel.getColumn(0).setPreferredWidth(300);
                    columnModel.getColumn(1).setPreferredWidth(300);
                    columnModel.getColumn(2).setPreferredWidth(300);
                    columnModel.getColumn(3).setPreferredWidth(300);
                    columnModel.getColumn(4).setPreferredWidth(300);
                    columnModel.getColumn(5).setPreferredWidth(300);
                    columnModel.getColumn(6).setPreferredWidth(300);
                    columnModel.getColumn(7).setPreferredWidth(300);

                    tablaLineas.fireTableDataChanged();
                }
                break;
            }
        }
        this.panel.revalidate();
    }

    public JComboBox<Cliente> getComboBoxCli() {
        return comboBoxCli;
    }

    public void setController(pos.presentation.facturar.Controller controller) {

        this.controller = controller;
    }
}


