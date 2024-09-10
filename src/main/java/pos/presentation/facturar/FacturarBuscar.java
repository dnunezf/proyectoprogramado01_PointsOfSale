package pos.presentation.facturar;

import pos.logic.Producto;
import pos.presentation.productos.TableModel;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class FacturarBuscar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel busquedaPanel;
    private JLabel descLabel;
    private JTextField descTxt;
    private JPanel listadoPanel;
    private JScrollPane listadoScrollPanel;
    private JTable list;
    private JButton search;
    private pos.presentation.productos.TableModel tableModel;
    private pos.presentation.productos.Controller controller;


    public FacturarBuscar() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setDescripcion(descTxt.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Inicializamos el TableModel con columnas específicas
        int[] cols = {pos.presentation.productos.TableModel.CODIGO, pos.presentation.productos.TableModel.DESCRIPCION,pos.presentation.productos.TableModel.UNIDAD,pos.presentation.productos.TableModel.PRECIO,pos.presentation.productos.TableModel.CATEGORIA,pos.presentation.productos.TableModel.EXISTENCIAS};
        tableModel = new TableModel(cols, List.of() );  // Lista vacía al inicio
        list.setModel(tableModel);  // Asociamos el TableModel a la JTable
    }

    public void updateProductList(List<Producto> productos) {
        int[] cols = {TableModel.CODIGO, TableModel.DESCRIPCION, TableModel.UNIDAD, TableModel.PRECIO, TableModel.CATEGORIA, TableModel.EXISTENCIAS};
        tableModel = new TableModel(cols, productos);  // Actualiza el TableModel con la lista de productos
        list.setModel(tableModel);  // Actualiza el JTable con el nuevo TableModel
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    public void setController(pos.presentation.productos.Controller controller) {
        this.controller = controller;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        FacturarBuscar dialog = new FacturarBuscar();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
