package pos.presentation.clientes;

import pos.Application;
import pos.logic.Cliente;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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

    public View()
    {

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
            idLabel.setToolTipText("Codigo requerido");
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
            }
        }
    }
}
