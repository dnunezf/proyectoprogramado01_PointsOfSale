package pos.presentation.cajeros;

import pos.Application;
import pos.logic.Cajero;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
    private JPanel datosCajero;
    private JLabel idLabel;
    private JTextField idTxt;
    private JLabel nombreLabel;
    private JTextField nombreTxt;
    private JButton save;
    private JButton delete;
    private JButton clear;
    private JPanel panelInside;

    public void initialize(JTabbedPane tabbedPane)
    {
        // Configuración del panel y tab
        Icon cajerosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/cashier.png"));
        tabbedPane.addTab("Cajeros  ", cajerosIcon, this.getPanel());
    }

    public View()
    {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Cajero filter = new Cajero();

                    filter.setNombre(busquedaNombreTxt.getText());

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
                    Cajero n = take();

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

        if (idTxt.getText().isEmpty())
        {
            valid = false;
            idLabel.setBorder(Application.BORDER_ERROR);
            idLabel.setToolTipText("Identificacion requerida");
        }
        else
        {
            idLabel.setBorder(null);
            idLabel.setToolTipText(null);
        }

        if (nombreTxt.getText().isEmpty())
        {
            valid = false;
            nombreLabel.setBorder(Application.BORDER_ERROR);
            nombreLabel.setToolTipText("Nombre requerido");
        }
        else
        {
            nombreLabel.setBorder(null);
            nombreLabel.setToolTipText(null);
        }

        return valid;
    }

    public Cajero take()
    {
        Cajero cajero = new Cajero();

        cajero.setId(idTxt.getText());
        cajero.setNombre(nombreTxt.getText());

        return cajero;
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
                int[] cols = {TableModel.ID, TableModel.NOMBRE};

                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);

                TableColumnModel columnModel = list.getColumnModel();

                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);

                break;
            }

            case Model.CURRENT:
            {
                idTxt.setText(model.getCurrent().getId());
                nombreTxt.setText(model.getCurrent().getNombre());

                if(model.getMode() == Application.MODE_EDIT)
                {
                    idTxt.setEnabled(false);
                    delete.setEnabled(true);
                }
                else
                {
                    idTxt.setEnabled(true);
                    delete.setEnabled(false);
                }

                idLabel.setBorder(null);
                idLabel.setToolTipText(null);

                nombreLabel.setBorder(null);
                nombreLabel.setToolTipText(null);

                break;
            }

            case Model.FILTER:
            {
                busquedaNombreTxt.setText(model.getFilter().getNombre());
                break;
            }
        }

        this.panel.revalidate();
    }

}
