package pos.presentation.facturar;

import pos.Application;
import pos.logic.Cajero;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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



    /*M.V.C*/
    Model model;
    Controller controller;



    public View() {
        cobrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FacturarCobrar cobrar = new FacturarCobrar();
                cobrar.setSize(450, 400);
                cobrar.setLocationRelativeTo(null);
                cobrar.setVisible(true);

            }
        });


        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturarBuscar buscar = new FacturarBuscar();
                buscar.setController(controller);
                buscar.setSize(400, 400);
                buscar.setLocationRelativeTo(null);
                buscar.setVisible(true);
            }
        });
        cantidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FacturarCantidad cantidad = new FacturarCantidad();
                cantidad.setController(controller);
                cantidad.setSize(400, 400);
                cantidad.setLocationRelativeTo(null);
                cantidad.setVisible(true);

            }
        });
        quitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        descuentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FacturarDescuento descuento = new FacturarDescuento();
                descuento.setController(controller);
                descuento.setSize(400, 400);
                descuento.setLocationRelativeTo(null);
                descuento.setVisible(true);

            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // En View.java, maneja mejor las excepciones.
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setCodigo(productCode.getText());
                    controller.add(filter); // Asegúrate de que 'controller' está correctamente inicializado
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Error al agregar producto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


    }

    public void initialize(JTabbedPane tabbedPane, pos.presentation.cajeros.Model modelCajeros, pos.presentation.clientes.Model modelClientes) {
        // Configuración del panel y tab
        Icon facturaIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/bill.png"));
        tabbedPane.addTab("Facturar  ", facturaIcon, this.getPanel());


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

    public Controller getController() {
        return this.controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.facturar.Model.LIST: {
                int[] cols = {pos.presentation.facturar.TableModel.CODIGO, pos.presentation.facturar.TableModel.ARTICULO, TableModel.CATEGORIA,
                        pos.presentation.facturar.TableModel.CANTIDAD, pos.presentation.facturar.TableModel.PRECIO, pos.presentation.facturar.TableModel.DESCUENTO,
                        pos.presentation.facturar.TableModel.NETO, pos.presentation.facturar.TableModel.IMPORTE};

                if(model.getList() != null) {
                    TableModel tablaLineas = new TableModel(cols, model.getList());

                    list.setModel(tablaLineas);
                    list.setRowHeight(30);

                    TableColumnModel columnModel = list.getColumnModel();

                    columnModel.getColumn(0).setPreferredWidth(150);
                    columnModel.getColumn(1).setPreferredWidth(150);
                    columnModel.getColumn(2).setPreferredWidth(150);
                    columnModel.getColumn(3).setPreferredWidth(170);
                    columnModel.getColumn(4).setPreferredWidth(150);
                    columnModel.getColumn(5).setPreferredWidth(170);
                    columnModel.getColumn(6).setPreferredWidth(150);
                    columnModel.getColumn(7).setPreferredWidth(150);

                    tablaLineas.fireTableDataChanged();
                }
                break;
            }

            case Model.CURRENT: {


                break;
            }

            case pos.presentation.productos.Model.FILTER: {


                break;
            }




        }

        this.panel.revalidate();
        this.panel.repaint();
    }
}


