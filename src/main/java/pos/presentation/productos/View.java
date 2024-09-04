package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.presentation.productos.*;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

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
    private JComboBox<Categoria> catComboBox;

    public void initialize(JTabbedPane tabbedPane)
    {
        // Configuración del panel y tab
        Icon productosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/product.png"));
        tabbedPane.addTab("Productos  ", productosIcon, this.getPanel());

        // Inicializa JComboBox con categorías predefinidas
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();

        // Lista de categorías predefinidas
        List<Categoria> categoriasPredefinidas = Arrays.asList(
                new Categoria("Dulces"),
                new Categoria("Bebidas"),
                new Categoria("Abarrotes"),
                new Categoria("Lácteos"),
                new Categoria("Congelados"),
                new Categoria("Panadería"),
                new Categoria("Carnes"),
                new Categoria("Frutas y Verduras"),
                new Categoria("Cereales"),
                new Categoria("Galletas"),
                new Categoria("Snacks"),
                new Categoria("Productos de Limpieza"),
                new Categoria("Cuidado Personal"),
                new Categoria("Productos en Conserva"),
                new Categoria("Salsas y Condimentos"),
                new Categoria("Comida Rápida"),
                new Categoria("Pasta y Arroz"),
                new Categoria("Aceites y Grasas"),
                new Categoria("Productos para Bebés"),
                new Categoria("Vinos y Licores"),
                new Categoria("Ingredientes para Repostería"),
                new Categoria("Alimentos Internacionales"),
                new Categoria("Productos Orgánicos"),
                new Categoria("Café y Té")
        );

        // Añade las categorías al modelo del JComboBox
        for (Categoria categoria : categoriasPredefinidas) {
            model.addElement(categoria);
        }

        // Asigna el modelo al JComboBox
        catComboBox.setModel(model);
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

        try
        {
            Double.parseDouble(precioTxt.getText());
            precioLabel.setBorder(null);
            precioLabel.setToolTipText(null);
        }
        catch (Exception e)
        {
            valid = false;
            precioLabel.setBorder(Application.BORDER_ERROR);
            precioLabel.setToolTipText("Precio invalido");
        }

        try
        {
            Integer.parseInt(existTxt.getText());
            existLabel.setBorder(null);
            existLabel.setToolTipText(null);
        }
        catch (Exception e)
        {
            valid = false;
            existLabel.setBorder(Application.BORDER_ERROR);
            existLabel.setToolTipText("Existencia invalida");
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
        }

        this.panel.revalidate();
    }
}
