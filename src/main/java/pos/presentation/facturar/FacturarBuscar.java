package pos.presentation.facturar;

import pos.logic.Producto;
import pos.logic.Service;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class FacturarBuscar extends JDialog implements PropertyChangeListener {
    private JPanel contentPane;  // Panel principal
    private JPanel busquedaPanel;  // Panel de búsqueda agregado
    private JPanel listadoPanel;  // Panel para la tabla de productos
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField descTxt;
    private JButton search;
    private JTable list;
    private JLabel descLabel;
    private JScrollPane listadoScrollPanel;
    private pos.presentation.productos.TableModel tableModel;
    private pos.presentation.productos.Controller controllerProd;
    private List<Producto> productos;
    private Model model;
    private Producto current;
    boolean buscar = true;


    // Constructor
    public FacturarBuscar() {
        buttonOK.setEnabled(false);

        setContentPane(contentPane);
        setModal(true);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // Evento de búsqueda
        search.addActionListener(e -> onSearch());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // Cerrar con ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = list.getSelectedRow();
                if(row > -1)
                    buttonOK.setEnabled(true);
                else
                    buttonOK.setEnabled(false);
                // Verifica si hay una fila seleccionada
                if (row != -1) {
                    current = productos.get(row); // Asigna el producto seleccionado a current

                    // Verifica si se ha hecho un doble clic
                    if (e.getClickCount() == 2) {
                        buscar = true;
                        dispose(); // Cierra el diálogo
                    }
                }
            }
        });
    }

    // Configuración de la interfaz gráfica



    public Producto getCurrent() {
        return current;
    }

    // Acción al presionar OK
    public void onOK() {
        buscar = true;
        dispose();
    }

    // Acción al presionar Cancel
    private void onCancel() {
        buscar = false;
        dispose();

    }

    public boolean isBuscar() {
        return buscar;
    }

    // Evento de búsqueda
    private void onSearch() {
        try {
            Producto filtro = new Producto();
            filtro.setDescripcion(descTxt.getText());
            List<Producto> resultados = Service.getInstance().search(filtro);
            updateProductList(resultados);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(contentPane, ex.getMessage(),
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Actualiza la lista de productos en la tabla
    public void updateProductList(List<Producto> productos) {
        this.productos = productos;

        int[] cols = {
                pos.presentation.productos.TableModel.CODIGO,
                pos.presentation.productos.TableModel.DESCRIPCION,
                pos.presentation.productos.TableModel.UNIDAD,
                pos.presentation.productos.TableModel.PRECIO,
                pos.presentation.productos.TableModel.CATEGORIA,
                pos.presentation.productos.TableModel.EXISTENCIAS
        };

        tableModel = new pos.presentation.productos.TableModel(cols, productos);
        list.setModel(tableModel);
        tableModel.fireTableDataChanged();
        list.repaint();
    }

    // Setea el controlador
    public void setController(pos.presentation.productos.Controller controllerProd) {
        this.controllerProd = controllerProd;
    }

    // Setea el modelo de la tabla
    public void setTableModel() {
        int[] cols = {
                pos.presentation.productos.TableModel.CODIGO,
                pos.presentation.productos.TableModel.DESCRIPCION,
                pos.presentation.productos.TableModel.UNIDAD,
                pos.presentation.productos.TableModel.PRECIO,
                pos.presentation.productos.TableModel.CATEGORIA,
                pos.presentation.productos.TableModel.EXISTENCIAS
        };
        this.tableModel = new pos.presentation.productos.TableModel(cols,productos);
        list.setModel(tableModel);
    }

    // Setea la lista de productos
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        updateProductList(productos);
    }

    // Evento de cambio de propiedad
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (pos.presentation.productos.Model.LIST.equals(evt.getPropertyName())) {
            updateProductList((List<Producto>) evt.getNewValue());
        }
    }

    // Main para pruebas
    public static void main(String[] args) {
        FacturarBuscar dialog = new FacturarBuscar();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
