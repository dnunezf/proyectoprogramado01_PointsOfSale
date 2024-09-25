package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;

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
import java.util.function.Consumer;

public class View implements PropertyChangeListener
{
    private JPanel panel;
    private JTextField busquedaNombreTxt;
    private JButton search;
    private JButton save;
    private JTable list;
    private JButton delete;
    private JLabel busquedaNombreLabel;
    private JButton report;
    private JTextField idTxt;
    private JTextField nombreTxt;
    private JTextField emailTxt;
    private JLabel idLabel;
    private JLabel nombreLabel;
    private JLabel emailLabel;
    private JButton clear;
    private JLabel telefonoLabel;
    private JTextField telefonoTxt;
    private JLabel descuentoLabel;
    private JTextField descuentoTxt;
    private JPanel datosClientePanel;
    private JPanel busquedaPanel;
    private JPanel listadoPanel;
    private JScrollPane listadoScrollPanel;
    pos.presentation.facturar.View view;

    public void initialize(JTabbedPane tabbedPane, pos.presentation.facturar.View view)
    {
        // Configuración del panel y tab
        Icon clientesIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/client.png"));
        tabbedPane.addTab("Clientes  ", clientesIcon, this.getPanel());
        this.view = view;
    }

    public View()
    {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    Cliente filter = new Cliente();

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
                    Cliente n = take();

                    try
                    {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel,"REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                    catch (Exception ex)
                    {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    view.actualizarComboBoxes();

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
                        File myFile = new File("clientes.pdf");
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

        if (telefonoTxt.getText().isEmpty())
        {
            valid = false;
            telefonoLabel.setBorder(Application.BORDER_ERROR);
            telefonoLabel.setToolTipText("Telefono requerido");
        }
        else
        {
            telefonoLabel.setBorder(null);
            telefonoLabel.setToolTipText(null);
        }

        if (emailTxt.getText().isEmpty())
        {
            valid = false;
            emailLabel.setBorder(Application.BORDER_ERROR);
            emailLabel.setToolTipText("Email requerido");
        }
        else
        {
            emailLabel.setBorder(null);
            emailLabel.setToolTipText(null);
        }

        try
        {
            Float.parseFloat(descuentoTxt.getText());
            descuentoLabel.setBorder(null);
            descuentoLabel.setToolTipText(null);
        }
        catch (Exception e)
        {
            valid = false;
            descuentoLabel.setBorder(Application.BORDER_ERROR);
            descuentoLabel.setToolTipText("Descuento invalido");
        }

        return valid;
    }

    public Cliente take()
    {
        Cliente cliente = new Cliente();

        cliente.setId(idTxt.getText());
        cliente.setNombre(nombreTxt.getText());
        cliente.setTelefono(telefonoTxt.getText());
        cliente.setEmail(emailTxt.getText());
        cliente.setDescuento(Float.parseFloat(descuentoTxt.getText()));

        return cliente;
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
                int[] cols = {TableModel.ID, TableModel.NOMBRE, TableModel.TELEFONO, TableModel.EMAIL, TableModel.DESCUENTO};

                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);

                TableColumnModel columnModel = list.getColumnModel();

                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);

                break;
            }

            case Model.CURRENT:
            {
                idTxt.setText(model.getCurrent().getId());
                nombreTxt.setText(model.getCurrent().getNombre());
                telefonoTxt.setText(model.getCurrent().getTelefono());
                emailTxt.setText(model.getCurrent().getEmail());
                descuentoTxt.setText("" + model.getCurrent().getDescuento());

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

                emailLabel.setBorder(null);
                emailLabel.setToolTipText(null);

                telefonoLabel.setBorder(null);
                telefonoLabel.setToolTipText(null);

                descuentoLabel.setBorder(null);
                descuentoLabel.setToolTipText(null);

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
