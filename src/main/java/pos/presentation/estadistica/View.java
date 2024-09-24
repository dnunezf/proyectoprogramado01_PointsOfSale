package pos.presentation.estadistica;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import pos.Application;
import pos.logic.Categoria;
import pos.logic.Estadistica;
import pos.presentation.cajeros.DynamicTableModel;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.table.TableColumnModel;
import java.io.IOException;
import java.time.LocalDate;

public class View implements PropertyChangeListener {
    private JComboBox<Integer> anioDesdeComboBox;
    private JComboBox<Integer> mesDesdeCombobox;
    private JComboBox<Integer> anioHastaCombobox;
    private JComboBox<Integer> mesHastaCombobox;
    private JComboBox<Categoria> categoriaCombobox;
    private JButton check;
    private JButton dobleCheck;
    private JTable table;  // Modificar aquí: asegurarse de usar "table" en lugar de "list"
    private JPanel panel;
    private JPanel panelStat;
    private JLabel chart;
    private JPanel panelPrincipal;
    private JLabel desdeLabel;
    private JLabel categoriaLabel;

    // MVC
    private Model model;
    private Controller controller;
    private DynamicTableModel tableModel;  // Nuestro TableModel dinámico

    public View()
    {
        // Constructor vacío
    }

    public void initialize(JTabbedPane tabbedPane) {
        // Configuración del panel y tab
        Icon estadisticasIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/stadistic.png"));
        tabbedPane.addTab("Estadisticas  ", estadisticasIcon, this.getPanel());

        // Inicializa JComboBox con categorías predefinidas
        DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();

        // Añadir las categorías al JComboBox
        for (Categoria categoria : this.model.getList()) {
            model.addElement(categoria);
        }
        categoriaCombobox.setModel(model);


        // Crear el dataset para el gráfico
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();


        // Crear el gráfico de barras
        JFreeChart fchart = ChartFactory.createBarChart(
                "Ventas",   // título del gráfico
                "Categoria",     // etiqueta de categoría
                "Valor",        // etiqueta de valor
                dataset,        // conjunto de datos
                PlotOrientation.VERTICAL, // orientación
                false,          // incluir leyenda
                true,           // incluir tooltips
                false           // no se requieren URLs
        );

        // Convertir el gráfico a JPG y guardarlo en un archivo
        File chartFile = new File("chart.jpg");
        try {
            ChartUtilities.saveChartAsJPEG(chartFile, fchart, 300, 550);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cargar la imagen JPG y asignarla al JLabel
        ImageIcon chartIcon = new ImageIcon(chartFile.getPath());
        chart = new JLabel(chartIcon);

        // Asegurarse de añadir el JLabel al panelStat
        panelStat.setLayout(new BorderLayout());
        panelStat.removeAll();  // Limpiar el panel si es necesario
        panelStat.add(chart, BorderLayout.CENTER);  // Añadir el JLabel con el gráfico
        panelStat.revalidate();  // Actualizar el panel
        panelStat.repaint();     // Refrescar la interfaz

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


    public Estadistica take(){

        return new Estadistica(LocalDate.of((int)this.anioDesdeComboBox.getSelectedItem(),(int)this.mesDesdeCombobox.getSelectedItem(),1) ,LocalDate.of((int)this.anioHastaCombobox.getSelectedItem(),(int)this.mesHastaCombobox.getSelectedItem(),1));

    }



    // Método que responde a los cambios de propiedades del modelo (MVC)
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LIST: {
                try {
                    int month1 = (int) anioDesdeComboBox.getSelectedItem();
                    int year1 = (int) mesDesdeCombobox.getSelectedItem();
                    int month2 = (int) anioHastaCombobox.getSelectedItem();
                    int year2 = (int) mesHastaCombobox.getSelectedItem();

                    // Verificar que los valores sean válidos
                    if (month1 < 1 || month1 > 12 || month2 < 1 || month2 > 12 ||
                            year1 > year2 || (year1 == year2 && month1 > month2)) {
                        throw new IllegalArgumentException("Rango de fechas no válido");
                    }

                    // Crear el TableModel con la lista de categorías del modelo
                    tableModel = new DynamicTableModel(this.model.getList(),this.take());

                    //tableModel.updateDateRange(this.controller.getHistoricoModel().getListBills(),month1, year1, month2, year2);

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
