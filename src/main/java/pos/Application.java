/*Proyecto programado #1 "POS: Points Of Sale"

  17 de septiembre de 2024

  Autores:
  David Alberto Núñez Franco
  Cedula: 119080008
  Jose David Alvarado Guerrero
  Cedula: 119040654
  Alejandro Alvarez Chaves
  Cedula: 118910804

  Universidad Nacional de Costa Rica, Escuela de Informática
  PROGRAMACIÓN III
*/

package pos;

import pos.logic.Service;
import pos.presentation.facturar.FacturarBuscar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static pos.presentation.facturar.Controller facturarController = null;
    public static pos.presentation.clientes.Controller clientesController = null;
    public static pos.presentation.cajeros.Controller cajerosController = null;
    public static pos.presentation.productos.Controller productosController = null;
    ;
    public static pos.presentation.estadistica.Controller estadisticaController = null;
    public static pos.presentation.historico.Controller historicoController = null;

    public static JFrame window;

    public final static int MODE_CREATE = 1;
    public final static int MODE_EDIT = 2;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
        }
        ;

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.getInstance().stop();
            }
        });

        // 1. Inicializa los modelos
        pos.presentation.facturar.Model facturarModel = new pos.presentation.facturar.Model();
        pos.presentation.clientes.Model clientesModel = new pos.presentation.clientes.Model();
        pos.presentation.cajeros.Model cajerosModel = new pos.presentation.cajeros.Model();
        pos.presentation.productos.Model productosModel = new pos.presentation.productos.Model();
      //  pos.presentation.estadistica.Model estadisticaModel = new pos.presentation.estadistica.Model();
//        pos.presentation.historico.Model historicoModel = new pos.presentation.historico.Model();

        // 2. Inicializa las vistas
        pos.presentation.facturar.View facturarView = new pos.presentation.facturar.View();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        pos.presentation.cajeros.View cajerosView = new pos.presentation.cajeros.View();
        pos.presentation.productos.View productosView = new pos.presentation.productos.View();
      //  pos.presentation.estadistica.View estadisticaView = new pos.presentation.estadistica.View();
//        pos.presentation.historico.View historicoView = new pos.presentation.historico.View();

        // 3. Inicializa los controladores
        facturarController = new pos.presentation.facturar.Controller(facturarView, facturarModel,productosController);
        clientesController = new pos.presentation.clientes.Controller(clientesView, clientesModel);
        cajerosController = new pos.presentation.cajeros.Controller(cajerosView, cajerosModel);
        productosController = new pos.presentation.productos.Controller(productosView, productosModel);
        facturarController = new pos.presentation.facturar.Controller(facturarView, facturarModel,productosController);
       // estadisticaController = new pos.presentation.estadistica.Controller(estadisticaView, estadisticaModel,historicoModel);
//        historicoController = new pos.presentation.historico.Controller(historicoView, historicoModel);
//
//        // 4. Pasa los controladores a las vistas ahora que están inicializados
        facturarView.subPanels(facturarController);

//        // 5. Inicializa las vistas
        facturarView.initialize(tabbedPane, cajerosModel, clientesModel);
        clientesView.initialize(tabbedPane,facturarView);
        cajerosView.initialize(tabbedPane,facturarView);
        productosView.initialize(tabbedPane);
       // estadisticaView.initialize(tabbedPane);
//        historicoView.initialize(tabbedPane);

        // 6. Configuración de la ventana
        window.setSize(790, 700);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("POS: Point Of Sale");
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
