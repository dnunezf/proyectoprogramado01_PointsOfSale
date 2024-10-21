package pos.presentation.facturar;

import pos.Application;
import pos.logic.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

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
    private FacturarBuscar buscar;
    private FacturarCobrar cobrar;

    /*M.V.C*/
    Model model;
    pos.presentation.historico.Controller historicoController;
    pos.presentation.facturar.Controller controller;
    pos.presentation.productos.Controller productosController;
    pos.presentation.productos.TableModel tableModelProd;
    pos.presentation.clientes.Model clientesModel;

    public View()
    {
        setTableModelProd();

        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cobrar != null) {  // Asegúrate de que 'cobrar' no sea nulo
                    cobrar.setSize(550, 400);
                    cobrar.setLocationRelativeTo(null);
                    cobrar.setVisible(true);

                    // Limpiar la lista de productos
                    model.getList().clear();

                    // Actualizar el modelo para reflejar los cambios
                    model.setList(model.getList());

                    // Actualizar las etiquetas
                    actualizarLabels();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: no se puede abrir la ventana de cobro.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Crear una instancia de FacturarBuscar

                    // Configurar la ventana de búsqueda
                    buscar.setController(productosController);  // Asignar el controlador
                    buscar.setTableModel();        // Asignar el TableModel
                    buscar.setSize(400, 400);                    // Tamaño de la ventana
                    buscar.setLocationRelativeTo(null);          // Centrar en pantalla

                    // Realizar la búsqueda inicial y pasar los productos encontrados
                    List<Producto> productos = Service.getInstance().search(new Producto());
                    buscar.setProductos(productos);              // Establecer productos en la tabla
                    buscar.updateProductList(productos);         // Actualizar la lista en la tabla

                    // Mostrar la ventana de búsqueda
                    buscar.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al buscar productos: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                if(buscar.buscar) {
                    if (buscar.getCurrent() != null) {
                        try {
                            controller.add(buscar.getCurrent());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "Error al agregar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        actualizarLabels();
                    }
                }
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
                try {
                    Producto filter = new Producto();
                    filter.setCodigo(productCode.getText());
                    controller.add(filter);
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

        this.clientesModel = modelClientes;

        actualizarComboBoxes();
    }

    public JPanel getPanel() {
        return panel;
    }

    public Cliente getSelectedCliente(){
        return (Cliente) comboBoxCli.getSelectedItem();
    }

    public Cajero getSelectedCajero(){
        return (Cajero) comboBoxCaj.getSelectedItem();
    }

    public void actualizarLabels()
    {
        cantidadArticulos.setText(Integer.toString(controller.getCantidadArticulos()));
        subTotal.setText(Float.toString(controller.getSubtotal()));
        descuentos.setText(Float.toString(controller.getDescuentos()));
        total.setText(Float.toString(controller.getTotal()));
    }

    public void subPanels(Controller facturarController)
    {
        // Pasa la instancia actual de 'View' (es decir, 'this') al constructor de FacturarCobrar
        cobrar = new FacturarCobrar(facturarController, this, model);
        buscar = new FacturarBuscar();
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

    public void setHistoricoController(pos.presentation.historico.Controller historicoController){

        this.historicoController = historicoController;
    }

    public pos.presentation.facturar.FacturarBuscar getFacturarBuscar() {

        return this.buscar;

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

                    DefaultComboBoxModel<Cliente> comboBoxClientes = new DefaultComboBoxModel<>();

//                    for (Cliente cliente : clientesModel.getList()) {
//                        comboBoxClientes.addElement(cliente);
//                    }
//                    comboBoxCli.setModel(comboBoxClientes);

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

    public pos.presentation.facturar.FacturarCobrar getFacturarCobrar() {

        return this.cobrar;
    }

    public JComboBox<Cajero> getComboBoxCaj() {

        return comboBoxCaj;
    }

    public void actualizarComboBoxes(){
        DefaultComboBoxModel<Cajero> comboBoxCajeros = new DefaultComboBoxModel<>();

        // Añade las categorías al modelo del JComboBox
        for (Cajero cajero : Service.getInstance().search(new Cajero())) {
            comboBoxCajeros.addElement(cajero);
        }

        DefaultComboBoxModel<Cliente> comboBoxClientes = new DefaultComboBoxModel<>();

        for (Cliente cliente : Service.getInstance().search(new Cliente())) {
            comboBoxClientes.addElement(cliente);
        }

        // Asigna el modelo al JComboBox
        comboBoxCaj.setModel(comboBoxCajeros);
        comboBoxCli.setModel(comboBoxClientes);
    }

    public void setTableModelProd() {
        int[] cols = {pos.presentation.productos.TableModel.CODIGO, pos.presentation.productos.TableModel.DESCRIPCION, pos.presentation.productos.TableModel.UNIDAD, pos.presentation.productos.TableModel.PRECIO, pos.presentation.productos.TableModel.EXISTENCIAS, pos.presentation.productos.TableModel.CATEGORIA};
        this.tableModelProd = new pos.presentation.productos.TableModel(cols, Service.getInstance().search(new Producto()));
    }
}


