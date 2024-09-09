 package pos.presentation.estadistica;

 import pos.Application;
 import pos.logic.Categoria;

 import javax.swing.*;
 import java.beans.PropertyChangeEvent;
 import javax.swing.table.TableColumnModel;



 public class View {
     private JComboBox comboBox1;
     private JComboBox comboBox2;
     private JComboBox comboBox3;
     private JComboBox comboBox4;
     private JComboBox comboBox5;
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


     public void propertyChange(PropertyChangeEvent evt) {

         switch (evt.getPropertyName())
         {
             case Model.LIST:
             {
                 int[] cols = {TableModel.CATEGORIA, TableModel.FECHA1, TableModel.FECHA2, TableModel.FECHA3};

                 list.setModel(new TableModel(cols, model.getList()));
                 list.setRowHeight(30);

                 TableColumnModel columnModel = list.getColumnModel();

                 columnModel.getColumn(1).setPreferredWidth(250);
                 columnModel.getColumn(3).setPreferredWidth(250);

                 break;
             }

             case Model.CURRENT: {

                break;
             }

             case Model.FILTER:
             {

                 break;
             }
         }

         this.panel.revalidate();
     }
     }

