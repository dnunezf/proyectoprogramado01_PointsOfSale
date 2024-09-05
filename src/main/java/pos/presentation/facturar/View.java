package pos.presentation.facturar;

import pos.Application;

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
    private JPanel datosCajeros;
    private JPanel listadoFacturasPanel;
    private JPanel addProductPanel;
    private JLabel addProductLabel;
    private JTextField addProductTxt;
    private JButton addProduct;

    public void initialize(JTabbedPane tabbedPane)
    {
        // Configuración del panel y tab
        Icon facturaIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/bill.png"));
        tabbedPane.addTab("Facturar  ",  facturaIcon, this.getPanel());
    }

    public JPanel getPanel() {
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
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
