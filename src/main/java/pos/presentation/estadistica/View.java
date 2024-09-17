package pos.presentation.estadistica;

import pos.Application;
import pos.logic.Categoria;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.TableColumnModel;

public class View implements PropertyChangeListener {
    private JComboBox<Integer> comboBox1;
    private JComboBox<Integer> comboBox2;
    private JComboBox<Integer> comboBox3;
    private JComboBox<Integer> comboBox4;
    private JComboBox<Categoria> comboBox5;
    private JButton button1;
    private JButton button2;
    private JTable table;  // Modificar aquí: asegurarse de usar "table" en lugar de "list"
    private JPanel panel;
    private JPanel panelStat;

    // MVC
    private Model model;
    private Controller controller;
    private TableModel tableModel;  // Nuestro TableModel dinámico

    public View() {
        // Constructor vacío
    }

    public void initialize(JTabbedPane tabbedPane) {
        // Configuración del panel y tab
        Icon estadisticasIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/stadistic.png"));
        tabbedPane.addTab("Estadisticas  ", estadisticasIcon, this.getPanel());

        // Inicializa JComboBox con categorías predefinidas
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();

        // Lista de categorías predefinidas
        List<Categoria> categoriasPredefinidas = Arrays.asList(
                new Categoria("Dulces"),
                new Categoria("Bebidas"),
                // Otras categorías...
                new Categoria("Café y Té")
        );

        // Añadir las categorías al JComboBox
        for (Categoria categoria : categoriasPredefinidas) {
            model.addElement(categoria);
        }
        comboBox5.setModel(model);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    // Método que responde a los cambios de propiedades del modelo (MVC)
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST: {
                try {
                    int month1 = (int) comboBox1.getSelectedItem();
                    int year1 = (int) comboBox2.getSelectedItem();
                    int month2 = (int) comboBox3.getSelectedItem();
                    int year2 = (int) comboBox4.getSelectedItem();

                    // Verificar que los valores sean válidos
                    if (month1 < 1 || month1 > 12 || month2 < 1 || month2 > 12 ||
                            year1 > year2 || (year1 == year2 && month1 > month2)) {
                        throw new IllegalArgumentException("Rango de fechas no válido");
                    }

                    // Crear el TableModel con la lista de categorías del modelo
                    tableModel = new TableModel(model.getList());
                    tableModel.updateDateRange(this.controller.getHistoricoModel().getListBills(),month1, year1, month2, year2);

                    // Asignar el TableModel a la JTable
                    table.setModel(tableModel);  // Aquí usamos 'table' en lugar de 'list'

                    // Ajustar el ancho de las columnas
                    TableColumnModel columnModel = table.getColumnModel();  // Cambiado 'list' por 'table'
                    columnModel.getColumn(0).setPreferredWidth(150);  // Ajustar ancho para la columna "Categoria"

                    for (int i = 1; i < columnModel.getColumnCount(); i++) {
                        columnModel.getColumn(i).setPreferredWidth(100);  // Ajustar ancho para las columnas de fechas
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

            case Model.CURRENT: {
                // Manejar cambios en el estado actual si es necesario
                break;
            }

            case Model.FILTER: {
                // Manejar filtrado si es necesario
                break;
            }
        }

        // Refrescar la interfaz para reflejar los cambios
        this.panel.revalidate();
        this.panel.repaint();
    }
}
