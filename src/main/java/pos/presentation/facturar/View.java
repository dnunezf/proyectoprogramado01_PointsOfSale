package pos.presentation.facturar;

import javax.swing.*;
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
    private JComboBox comboBox1;
    private JPanel datosCajeroPanel;
    private JPanel listadoFacturasPanel;
    private JPanel addProductPanel;
    private JLabel addProductLabel;
    private JTextField addProductTxt;
    private JButton addProduct;


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
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
