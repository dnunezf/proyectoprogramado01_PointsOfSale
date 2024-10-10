package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.presentation.productos.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JPanel busquedaPanel;
    private JLabel busquedaNombreLabel;
    private JTextField busquedaNombreTxt;
    private JButton report;
    private JButton search;
    private JPanel listadoPanel;
    private JScrollPane listadoScrollPanel;
    private JTable list;
    private JPanel datosProductoPanel;
    private JLabel codLabel;
    private JTextField codTxt;
    private JLabel descLabel;
    private JTextField descTxt;
    private JLabel unidadLabel;
    private JTextField unidadTxt;
    private JLabel precioLabel;
    private JTextField precioTxt;
    private JLabel existLabel;
    private JButton save;
    private JButton delete;
    private JButton clear;
    private JTextField existTxt;
    private JLabel catLabel;
    private JComboBox catComboBox;

    public void initialize(JTabbedPane tabbedPane)
    {
        // Configuración del panel y tab
        Icon productosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/product.png"));
        tabbedPane.addTab("Productos  ", productosIcon, this.getPanel());
    }

    public View()
    {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Producto filter = new Producto();

                    filter.setDescripcion(busquedaNombreTxt.getText());

                    controller.search(filter);
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate())
                {
                    Producto n = take();

                    try
                    {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel,"REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });

        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    controller.print();

                    if (Desktop.isDesktopSupported())
                    {
                        File myFile = new File("productos.pdf");
                        Desktop.getDesktop().open(myFile);
                    }
                }
                catch (Exception ex)
                {

                }
            }
        });
    }

    public JPanel getPanel()
    {
        return panel;
    }

    private boolean validate()
    {
        boolean valid = true;

        if (codTxt.getText().isEmpty())
        {
            valid = false;
            codLabel.setBorder(Application.BORDER_ERROR);
            codLabel.setToolTipText("Codigo requerido");
        }
        else
        {
            codLabel.setBorder(null);
            codLabel.setToolTipText(null);
        }

        if (descTxt.getText().isEmpty())
        {
            valid = false;
            descLabel.setBorder(Application.BORDER_ERROR);
            descLabel.setToolTipText("Descripcion requerida");
        }
        else
        {
            descLabel.setBorder(null);
            descLabel.setToolTipText(null);
        }

        if (unidadTxt.getText().isEmpty())
        {
            valid = false;
            unidadLabel.setBorder(Application.BORDER_ERROR);
            unidadLabel.setToolTipText("Unidad requerida");
        }
        else
        {
            unidadLabel.setBorder(null);
            unidadLabel.setToolTipText(null);
        }

        try {
            double precio = Double.parseDouble(precioTxt.getText());
            if (precio < 0) {
                throw new Exception("El precio no puede ser negativo");
            }
            precioLabel.setBorder(null);
            precioLabel.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            precioLabel.setBorder(Application.BORDER_ERROR);
            precioLabel.setToolTipText("Precio invalido o negativo");
        }

        try {
            int existencias = Integer.parseInt(existTxt.getText());
            if (existencias < 0) {
                throw new Exception("Las existencias no pueden ser negativas");
            }
            existLabel.setBorder(null);
            existLabel.setToolTipText(null);
        } catch (Exception e) {
            valid = false;
            existLabel.setBorder(Application.BORDER_ERROR);
            existLabel.setToolTipText("Existencia invalida o negativa");
        }

        if (catComboBox.getSelectedItem() == null) {
            valid = false;
            catComboBox.setBorder(Application.BORDER_ERROR);
            catComboBox.setToolTipText("Categoria requerida");
        } else {
            catComboBox.setBorder(null);
            catComboBox.setToolTipText(null);
        }

        return valid;
    }

    public Producto take()
    {
        Producto producto = new Producto();

        producto.setCodigo(codTxt.getText());
        producto.setDescripcion(descTxt.getText());
        producto.setUnidad(unidadTxt.getText());
        producto.setPrecioUnitario(Double.parseDouble(precioTxt.getText()));
        producto.setExistencias(Integer.parseInt(existTxt.getText()));
        producto.setCategoria((Categoria) catComboBox.getSelectedItem());

        return producto;
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
            case Model.LIST:
            {
                int[] cols = {TableModel.CODIGO, TableModel.DESCRIPCION, TableModel.UNIDAD, TableModel.PRECIO, TableModel.EXISTENCIAS, TableModel.CATEGORIA};

                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);

                TableColumnModel columnModel = list.getColumnModel();

                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(100);
                columnModel.getColumn(3).setPreferredWidth(100);
                columnModel.getColumn(4).setPreferredWidth(100);
                columnModel.getColumn(5).setPreferredWidth(150);

                break;
            }

            case Model.CURRENT:
            {
                codTxt.setText(model.getCurrent().getCodigo());
                descTxt.setText(model.getCurrent().getDescripcion());
                unidadTxt.setText(model.getCurrent().getUnidad());
                precioTxt.setText("" + model.getCurrent().getPrecioUnitario());
                existTxt.setText("" + model.getCurrent().getExistencias());
                catComboBox.setSelectedItem(model.getCurrent().getCategoria());

                if(model.getMode() == Application.MODE_EDIT)
                {
                    codTxt.setEnabled(false);
                    delete.setEnabled(true);
                } else {
                    codTxt.setEnabled(true);
                    delete.setEnabled(false);
                }

                // Limpiar los bordes y tooltips
                codLabel.setBorder(null);
                codLabel.setToolTipText(null);

                descLabel.setBorder(null);
                descLabel.setToolTipText(null);

                unidadLabel.setBorder(null);
                unidadLabel.setToolTipText(null);

                precioLabel.setBorder(null);
                precioLabel.setToolTipText(null);

                existLabel.setBorder(null);
                existLabel.setToolTipText(null);

                catLabel.setBorder(null);
                catLabel.setToolTipText(null);

                break;
            }

            case Model.FILTER:
            {
                busquedaNombreTxt.setText(model.getFilter().getDescripcion());
                break;
            }

            case Model.CATEGORIAS:
                catComboBox.setModel(new DefaultComboBoxModel(model.getCategorias().toArray()));
                break;
        }

        this.panel.revalidate();
    }

}
