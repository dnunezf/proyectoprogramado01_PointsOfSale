package pos.presentation.historico;

import pos.Application;
import pos.logic.*;
import pos.presentation.historico.Controller;
import pos.presentation.historico.Model;
import pos.presentation.historico.TableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel busquedaPanel;
    private JLabel busquedaClienteLabel;
    private JTextField busquedaClienteTxt;
    private JButton search;
    private JPanel listadoPanel;
    private JScrollPane listadoScrollPanel;
    private JTable listFactura;
    private JTable listLineas;
    private JPanel ListadoPanelLineas;
    private JScrollPane JScrollPaneLineas;
    private JPanel panel;

    public void initialize(JTabbedPane tabbedPane)
    {
        // Configuración del panel y tab
        Icon historicoIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/list_historic.png"));
        tabbedPane.addTab(" Historico ", historicoIcon, this.getPanel());
    }

    public View()
    {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente filter = new Cliente();
                    filter.setNombre(busquedaClienteTxt.getText().trim()); // Asegúrate de eliminar espacios en blanco
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        listFactura.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = listFactura.getSelectedRow();
                if (selectedRow != -1) {
                    Factura facturaSeleccionada = model.getListBills().get(selectedRow);
                    if (facturaSeleccionada != null) {
                        try {
                            controller.loadLinesForSelectedBill(facturaSeleccionada);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "Error al cargar líneas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    public JPanel getPanel()
    {
        return panel;
    }

    /*M.V.C*/
    Model model;
    Controller controller;

    public void setModel(Model model)
    {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        switch (evt.getPropertyName()) {
            case Model.LIST_BILLS: {
                // Asegúrate de que la tabla se actualiza con la nueva lista de facturas
                int[] cols = {TableModel.NUMERO, TableModel.CLIENTE, TableModel.CAJERO, TableModel.FECHA, TableModel.IMPORTE};
                listFactura.setModel(new TableModel(cols, model.getListBills()));
                listFactura.setRowHeight(30);

                TableColumnModel columnModel = listFactura.getColumnModel();

                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);

                break;
            }

            case Model.LIST_LINES: {
                int[] cols = {pos.presentation.facturar.TableModel.CODIGO, pos.presentation.facturar.TableModel.ARTICULO, pos.presentation.facturar.TableModel.CATEGORIA, pos.presentation.facturar.TableModel.CANTIDAD, pos.presentation.facturar.TableModel.PRECIO, pos.presentation.facturar.TableModel.DESCUENTO, pos.presentation.facturar.TableModel.NETO, pos.presentation.facturar.TableModel.IMPORTE};
                if (model.getListLines() != null) {
                    pos.presentation.facturar.TableModel tablaLineas = new pos.presentation.facturar.TableModel(cols, model.getListLines());
                    listLineas.setModel(tablaLineas);
                    listLineas.setRowHeight(30);

                    TableColumnModel columnModel = listLineas.getColumnModel();
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

            case Model.FILTER: {
                busquedaClienteTxt.setText(model.getFilter().getNombre());
                break;
            }
        }

        this.panel.revalidate();
    }

    public void update() {
        // Refrescar la tabla de facturas
        int[] facturaCols = {
                TableModel.NUMERO, TableModel.CLIENTE, TableModel.CAJERO,
                TableModel.FECHA, TableModel.IMPORTE
        };
        listFactura.setModel(new TableModel(facturaCols, model.getListBills()));
        listFactura.setRowHeight(30);

        // Ajustar el ancho de columnas para la tabla de facturas
        TableColumnModel facturaColumnModel = listFactura.getColumnModel();
        facturaColumnModel.getColumn(1).setPreferredWidth(150); // Cliente
        facturaColumnModel.getColumn(3).setPreferredWidth(150); // Fecha

        // Refrescar la tabla de líneas si existen líneas disponibles
        if (model.getListLines() != null) {
            int[] lineaCols = {
                    pos.presentation.facturar.TableModel.CODIGO,
                    pos.presentation.facturar.TableModel.ARTICULO,
                    pos.presentation.facturar.TableModel.CATEGORIA,
                    pos.presentation.facturar.TableModel.CANTIDAD,
                    pos.presentation.facturar.TableModel.PRECIO,
                    pos.presentation.facturar.TableModel.DESCUENTO,
                    pos.presentation.facturar.TableModel.NETO,
                    pos.presentation.facturar.TableModel.IMPORTE
            };

            pos.presentation.facturar.TableModel lineasModel =
                    new pos.presentation.facturar.TableModel(lineaCols, model.getListLines());

            listLineas.setModel(lineasModel);
            listLineas.setRowHeight(30);

            // Ajustar el ancho de columnas para la tabla de líneas
            TableColumnModel lineasColumnModel = listLineas.getColumnModel();
            for (int i = 0; i < lineasColumnModel.getColumnCount(); i++) {
                lineasColumnModel.getColumn(i).setPreferredWidth(300);
            }

            lineasModel.fireTableDataChanged(); // Notificar cambios
        }

        // Sincronizar el texto del filtro con el modelo
        busquedaClienteTxt.setText(model.getFilter().getNombre());

        // Asegurarse de que la interfaz gráfica se actualice completamente
        panel.revalidate();
        panel.repaint();
    }
}
