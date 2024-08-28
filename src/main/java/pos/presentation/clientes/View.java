package pos.presentation.clientes;

import javax.swing.*;

public class View
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

    public JPanel getPanel()
    {
        return panel;
    }
}
