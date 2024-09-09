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
    private JButton report;
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
                try
                {
                    Cliente filter = new Cliente();

                    filter.setNombre(busquedaClienteTxt.getText());

                    controller.search(filter);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
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
        switch (evt.getPropertyName())
        {
            case Model.LIST_BILLS:
            {
                int[] cols = {TableModel.NUMERO, TableModel.CLIENTE, TableModel.CAJERO, TableModel.FECHA, TableModel.IMPORTE};

                listFactura.setModel(new TableModel(cols, model.getListBills()));
                listFactura.setRowHeight(30);

                TableColumnModel columnModel = listFactura.getColumnModel();

                columnModel.getColumn(0).setPreferredWidth(100);
                columnModel.getColumn(1).setPreferredWidth(100);
                columnModel.getColumn(2).setPreferredWidth(100);
                columnModel.getColumn(3).setPreferredWidth(100);
                columnModel.getColumn(4).setPreferredWidth(100);

                break;
            }

            case Model.LIST_LINES:
            {
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

            case Model.FILTER:
            {
                busquedaClienteTxt.setText(model.getFilter().getNombre());
                break;
            }
        }

        this.panel.revalidate();
    }
}
