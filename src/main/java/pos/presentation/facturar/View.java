package pos.presentation.facturar;

import pos.Application;
import pos.logic.Cajero;

import javax.swing.*;
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
    private JComboBox<String> comboBoxCli;
    private JPanel datosCajeros;
    private JPanel listadoFacturasPanel;
    private JPanel addProductPanel;
    private JLabel addProductLabel;
    private JTextField productCode;
    private JButton addProduct;
    private JComboBox<Cajero> comboBoxCaj;


    
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
    }

    public void initialize(JTabbedPane tabbedPane) {
        // Configuración del panel y tab
        Icon facturaIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/bill.png"));
        tabbedPane.addTab("Facturar  ", facturaIcon, this.getPanel());

//        DefaultComboBoxModel<Cajero> mode = new DefaultComboBoxModel<>();
//
//        pos.presentation.cajeros.Model modelCaj = new pos.presentation.cajeros.Model();
//        // Añade las categorías al modelo del JComboBox
//        for (Cajero cajero : modelCaj.getList()) {
//            mode.addElement(cajero);
//        }
//
//        // Asigna el modelo al JComboBox
//        comboBoxCaj.setModel(mode);
    }

    public JPanel getPanel() {
        return panel;
    }

    /*M.V.C*/
    Model model;
    Controller controller;

   // pos.presentation.cajeros.Model modelCaj;

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
//        switch (evt.getPropertyName()) {
//            case pos.presentation.facturar.Model.LIST: {
//                int[] cols = {pos.presentation.facturar.TableModel.CODIGO, pos.presentation.facturar.TableModel.ARTICULO, TableModel.CATEGORIA,
//                        pos.presentation.facturar.TableModel.CANTIDAD, pos.presentation.facturar.TableModel.PRECIO, pos.presentation.facturar.TableModel.DESCUENTO,
//                                    pos.presentation.facturar.TableModel.NETO, pos.presentation.facturar.TableModel.IMPORTE};
//
//                            list.setModel(new TableModel(cols, model.getList()));
//                            list.setRowHeight(30);
//
//                            TableColumnModel columnModel = list.getColumnModel();
//
//                            columnModel.getColumn(1).setPreferredWidth(150);
//                            columnModel.getColumn(2).setPreferredWidth(100);
//                            columnModel.getColumn(3).setPreferredWidth(100);
//                            columnModel.getColumn(4).setPreferredWidth(100);
//                            columnModel.getColumn(5).setPreferredWidth(150);
//                            columnModel.getColumn(6).setPreferredWidth(100);
//                            columnModel.getColumn(7).setPreferredWidth(100);
//                            columnModel.getColumn(8).setPreferredWidth(150);
//
//                            break;
//                        }
//
////                case Model.CURRENT://                     {
////                codTxt.setText(model.getCurrent().getCodigo());
////                descTxt.setText(model.getCurrent().getDescripcion());
////                unidadTxt.setText(model.getCurrent().getUnidad());
////                precioTxt.setText("" + model.getCurrent().getPrecioUnitario());
////                existTxt.setText("" + model.getCurrent().getExistencias());
////                catComboBox.setSelectedItem(model.getCurrent().getCategoria());
////
////                if(model.getMode() == Application.MODE_EDIT)
////                {
////                    codTxt.setEnabled(false);
////                    delete.setEnabled(true);
////                } else {
////                    codTxt.setEnabled(true);
////                    delete.setEnabled(false);
////                }
////
////                // Limpiar los bordes y tooltips
////                codLabel.setBorder(null);
////                codLabel.setToolTipText(null);
////
////                descLabel.setBorder(null);
////                descLabel.setToolTipText(null);
////
////                unidadLabel.setBorder(null);
////                unidadLabel.setToolTipText(null);
////
////                precioLabel.setBorder(null);
////                precioLabel.setToolTipText(null);
////
////                existLabel.setBorder(null);
////                existLabel.setToolTipText(null);
////
////                catLabel.setBorder(null);
////                catLabel.setToolTipText(null);
////
////                break;
//            }
//
////            case pos.presentation.productos.Model.FILTER:
////            {
//////                busquedaNombreTxt.setText(model.getFilter().getDescripcion());
//////                break;
////            }
////        }
////
////        this.panel.revalidate();
////        }
    }


}
