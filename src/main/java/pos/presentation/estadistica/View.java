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
     private JTable list;
     private JPanel panel;
     private JLabel stat;
     private JPanel panelStat;


     public View(){



     }

     public void initialize(JTabbedPane tabbedPane) {
         // Configuración del panel y tab
         Icon estadisticasIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/stadistic.png"));
         tabbedPane.addTab("Estadisticas  ", estadisticasIcon, this.getPanel());

         // Crear un ImageIcon con la ruta de la imagen
         ImageIcon imageIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/stadistic.png"));

         //Ingresar una imagen en el Label

         // Inicializa JComboBox con categorías predefinidas
         DefaultComboBoxModel<Categoria> model = new DefaultComboBoxModel<>();

         // Lista de categorías predefinidas
         List<Categoria> categoriasPredefinidas = Arrays.asList(
                 new Categoria("Dulces"),
                 new Categoria("Bebidas"),
                 new Categoria("Abarrotes"),
                 new Categoria("Lácteos"),
                 new Categoria("Congelados"),
                 new Categoria("Panadería"),
                 new Categoria("Carnes"),
                 new Categoria("Frutas y Verduras"),
                 new Categoria("Cereales"),
                 new Categoria("Galletas"),
                 new Categoria("Snacks"),
                 new Categoria("Productos de Limpieza"),
                 new Categoria("Cuidado Personal"),
                 new Categoria("Productos en Conserva"),
                 new Categoria("Salsas y Condimentos"),
                 new Categoria("Comida Rápida"),
                 new Categoria("Pasta y Arroz"),
                 new Categoria("Aceites y Grasas"),
                 new Categoria("Productos para Bebés"),
                 new Categoria("Vinos y Licores"),
                 new Categoria("Ingredientes para Repostería"),
                 new Categoria("Alimentos Internacionales"),
                 new Categoria("Productos Orgánicos"),
                 new Categoria("Café y Té")
         );

         // Añade las categorías al modelo del JComboBox
         for (Categoria categoria : categoriasPredefinidas) {
             model.addElement(categoria);
         }

         comboBox5.setModel(model);


//         // Crear un JLabel y establecer la imagen como su icono
//         stat = new JLabel(imageIcon);
//         stat.setSize(300, 300); // Ajusta el tamaño según sea necesario
//
//         // Crear un JPanel y añadir el JLabel con la imagen
//         panelStat = new JPanel();
//         panelStat.add(stat);
//
//         panel = new JPanel();
//         // Agregar el panelStat al panel principal si es necesario
//          this.getPanel().add(panelStat);
     }


     public JPanel getPanel(){

         return panel;
     }

     //MVC

     Model model;
     Controller controller;
     private TableModel tableModel;  // Nuestro TableModel dinámico

     public void setController(Controller controller) {
     }

     public Controller getController() {
         return controller;
     }

     public void setModel(Model model) {
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

                     tableModel = new TableModel(model.getList());
                     tableModel.updateDateRange(month1, year1, month2, year2);
                     list.setModel(tableModel);

                     // Ajustar el ancho de las columnas
                     TableColumnModel columnModel = list.getColumnModel();
                     columnModel.getColumn(0).setPreferredWidth(150);

                     for (int i = 1; i < columnModel.getColumnCount(); i++) {
                         columnModel.getColumn(i).setPreferredWidth(100);
                     }
                 } catch (Exception e) {
                     // Manejar errores
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
         this.panel.repaint();  // Forzar repintado si es necesario
     }
 }

